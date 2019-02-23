package com.emarbox.controller;

import com.emarbox.entity.AgentInfo;
import com.emarbox.service.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequestMapping(value = "/account/agent/image")
public class AgentImageController {
    @Autowired
    ServiceProvider serviceProvider;
    @Autowired
    private Environment environment;


    @Value("${account.advertiser.image.upload.preview_domain}")
    private String imgDomain;
    @Value("${account.advertiser.image.upload.url}")
    private String directory;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("cuid") Long cuid, @RequestParam("file") MultipartFile multipartfile,HttpServletRequest request)
            throws IllegalStateException, IOException {
        log.info("getOriginalFilename:" + multipartfile.getOriginalFilename());
        log.info("getName:" + multipartfile.getName());
        // 保存文件到临时目录
        String savePath = directory + multipartfile.getOriginalFilename();
        String fix = getFix(savePath);
        if (StringUtils.isEmpty(fix) || !(fix.equals("jpg") || fix.equals("gif") || fix.equals("png"))) {
            return new ResponseEntity<String>("图片格式只允许jpg,gif,png三种,请重新上传", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        String imageSignature = DigestUtils.md5DigestAsHex(multipartfile.getInputStream());
        AgentInfo agentInfo = serviceProvider.getAgentInfoService().getAgentInfo(AgentInfo.builder().qualificationSignature(imageSignature).build());
        if (agentInfo != null) {
            return new ResponseEntity<String>(agentInfo.getQualificationImg(), HttpStatus.OK);
        } else {
            File saveFile = new File(savePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            boolean nonProduction = Stream.of(environment.getActiveProfiles()).map(String::toLowerCase).allMatch(profile -> !"prod".equals(profile));
            String imgFrefix = nonProduction ? "agent_cert_dev" : "agent_cert";
            String namelast = imgFrefix.concat("_").concat(String.valueOf(System.currentTimeMillis())).concat(".").concat(fix);
            savePath = directory.concat(namelast);
            multipartfile.transferTo(new File(savePath));
            log.info(savePath);
            String resultPath = "";
            if (StringUtils.isEmpty(resultPath)) {
                resultPath = imgDomain.concat(namelast);
            }
            return new ResponseEntity<String>(resultPath, HttpStatus.OK);
        }
    }

    /**
     * 获取文件名称后缀
     *
     * @param fileContentType 根据file的文件类型
     * @return
     */
    public String getFix(String fileContentType) {
        if (StringUtils.isEmpty(fileContentType)) {
            return null;
        }
        String postfix = fileContentType.substring(fileContentType.lastIndexOf(".") + 1, fileContentType.length());
        postfix = postfix.toLowerCase();
        return postfix;
    }

}
