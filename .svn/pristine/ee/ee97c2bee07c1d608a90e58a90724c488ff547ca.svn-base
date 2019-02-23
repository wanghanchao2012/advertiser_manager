package com.emarbox.controller;

import com.emarbox.dto.ProjectInfoQuery;
import com.emarbox.dto.common.AdvertiserOptionItemVo;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.common.TreeVo;
import com.emarbox.entity.EmarboxProject;
import com.emarbox.enums.PlatformType;
import com.emarbox.enums.ValidType;
import com.emarbox.service.ServiceProvider;
import com.emarbox.service.validator.ProjectInfoValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account/project/v2")
public class EmarboxProjectController {

    @Autowired
    ServiceProvider serviceProvider;
    @Autowired
    ProjectInfoValidatorService validatorService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseResult<List<EmarboxProject>> list(@RequestBody ProjectInfoQuery query) {
        return serviceProvider.getEmarboxProjectService().getPage(query);
    }


    @RequestMapping(value="/search/source", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> searchSource() {
        Map<String, Object> source = new HashMap<>();
        List<OptionItemVo> projectOptions = serviceProvider.getEmarboxProjectService().getProjectOptions();
        List<OptionItemVo> agentInfos = serviceProvider.getAgentInfoService().getAgentInfos();
        List<AdvertiserOptionItemVo> advertiserOptions = serviceProvider.getAdvertiserInfoService().getAdvertiserOptions();
        List<OptionItemVo> salesmanOptions = serviceProvider.getSalesmanService().getSalesmanOptions();
        List<OptionItemVo> departmentOptions = serviceProvider.getDepartmentService().getDepartmentOptions();
        source.put("project_info_list",projectOptions);
        source.put("agent_name_list", agentInfos);
        source.put("advertiser_info_list", advertiserOptions);
        source.put("salesman_list", salesmanOptions);
        source.put("department_list", departmentOptions);
        return ResponseResult.<Map<String, Object>>builder().data(source).build().forOk();
    }


    @RequestMapping(value="/todoedit", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> todoEdit(@RequestParam(value = "projectId", required = false) Long projectId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> source = new HashMap<>();
        List<OptionItemVo> platformList = EnumSet.allOf(PlatformType.class).stream().map(e->OptionItemVo.builder().value(e.getIndex()).text(e.getDesc()).build()).collect(Collectors.toList());
        List<AdvertiserOptionItemVo> advertiserOptions = serviceProvider.getAdvertiserInfoService().getAdvertiserOptionsEdit();
        List<OptionItemVo> salesmanOptions = serviceProvider.getSalesmanService().getSalesmanOptionsEdit();
        List<OptionItemVo> departmentOptions = serviceProvider.getDepartmentService().getDepartmentOptionsEdit();
        EmarboxProject projectInfo = null;
        if (Objects.nonNull(projectId)) {
            projectInfo = serviceProvider.getEmarboxProjectService().getProjectInfo(projectId);
        }
        source.put("platform", platformList);
        source.put("salesmans", salesmanOptions);
        source.put("departments", departmentOptions);
        source.put("advertiser", advertiserOptions);
        result.put("source", source);
        result.put("info", projectInfo);
        return ResponseResult.<Map<String, Object>> builder().data(result).build().forOk();
    }


    @RequestMapping("/industry")
    @ResponseBody
    public ResponseResult<Object> industry(Long platformId) {
        List<TreeVo> industryOptions = serviceProvider.getPlatformIndustryInfoService().getIndustryOptions(platformId);
        return ResponseResult.builder().data(industryOptions).build().forOk();
    }


    @RequestMapping(value="/doedit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doEdit(Long curd, @RequestBody EmarboxProject projectInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        projectInfo.setUserId(curd);
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.UPDATE, projectInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        ResponseResult<Map<String, Object>> result = serviceProvider.getEmarboxProjectService().edit(projectInfo);
        return result;
    }


    @RequestMapping(value="/doadd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doAdd(Long curd, @RequestBody EmarboxProject projectInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        projectInfo.setUserId(curd);
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.ADD, projectInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        ResponseResult<Map<String, Object>> result = serviceProvider.getEmarboxProjectService().add(projectInfo);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
    }
}
