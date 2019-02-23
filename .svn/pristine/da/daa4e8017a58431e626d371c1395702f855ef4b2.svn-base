package com.emarbox.controller;

import com.emarbox.dto.AdvertiserInfoQuery;
import com.emarbox.dto.common.AdvertiserOptionItemVo;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.entity.AdvertiserInfo;
import com.emarbox.enums.AdvertiserQualificationType;
import com.emarbox.enums.StatusType;
import com.emarbox.enums.ValidType;
import com.emarbox.service.ServiceProvider;
import com.emarbox.service.validator.AdvertiserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account/advertiser")
public class AdvertiserInfoController {

    @Autowired
    ServiceProvider serviceProvider;
    @Autowired
    AdvertiserValidatorService validatorService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseResult<List<AdvertiserInfo>> list(@RequestBody AdvertiserInfoQuery query) {
        return serviceProvider.getAdvertiserInfoService().getPage(query);
    }

    @RequestMapping(value="/search/source", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> searchSource() {
        Map<String, Object> source = new HashMap<>();
        List<OptionItemVo> agentInfos = serviceProvider.getAgentInfoService().getAgentInfos();
        List<AdvertiserOptionItemVo> advertiserOptions = serviceProvider.getAdvertiserInfoService().getAdvertiserOptions();
        source.put("advertiser_name_list", advertiserOptions);
        source.put("agent_name_list", agentInfos);
        List<OptionItemVo> collectStatus = new ArrayList<>();
        collectStatus.add(OptionItemVo.builder().text("全部").value("").build());
        collectStatus.addAll(EnumSet.allOf(StatusType.class).stream().map(e -> OptionItemVo.builder().value(e.name()).text(e.getDesc()).build()).collect(Collectors.toList()));
        source.put("status_list", collectStatus);
        return ResponseResult.<Map<String, Object>>builder().data(source).build().forOk();
    }

    @RequestMapping("/todoedit")
    @ResponseBody
    public ResponseResult<Map<String, Object>> todoEdit(@RequestParam(value = "advertiserId", required = false) Long advertiserId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> source = new HashMap<>();
        List<OptionItemVo> projectCategoryOptionList = serviceProvider.getEmarboxProjectCategoryService().getProjectCategoryOptionList();
        List<OptionItemVo> agentInfos = serviceProvider.getAgentInfoService().getAgentInfosEdit();
        AdvertiserInfo advertiserInfo = null;
        if (Objects.nonNull(advertiserId)) {
            advertiserInfo = serviceProvider.getAdvertiserInfoService().getAdvertiserInfo(advertiserId);
        }
        source.put("agent_info", agentInfos);
        source.put("category", projectCategoryOptionList);
        source.put("certificate_list", EnumSet.allOf(AdvertiserQualificationType.class).stream().collect(Collectors.toMap(e -> e.getIndex(), e -> e.getDesc())));
        result.put("source", source);
        result.put("info", advertiserInfo);
        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }


    @RequestMapping(value="/doedit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doEdit(@RequestBody AdvertiserInfo advertiserInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.UPDATE, advertiserInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        advertiserInfo.setCurrentUserId(cuid);
        Map<String, Object> result = new HashMap<>();
        serviceProvider.getAdvertiserInfoService().edit(advertiserInfo);
        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }


    @RequestMapping(value="/doadd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doAdd(@RequestBody AdvertiserInfo advertiserInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.ADD, advertiserInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        advertiserInfo.setCurrentUserId(cuid);
        Map<String, Object> result = new HashMap<>();
        serviceProvider.getAdvertiserInfoService().add(advertiserInfo);

        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }
}
