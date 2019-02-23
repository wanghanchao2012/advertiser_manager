package com.emarbox.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emarbox.dto.AgentInfoQuery;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.entity.AgentInfo;
import com.emarbox.enums.QualificationType;
import com.emarbox.enums.StatusType;
import com.emarbox.enums.ValidType;
import com.emarbox.service.ServiceProvider;
import com.emarbox.service.validator.AgentValidatorService;

@Controller
@RequestMapping("/account/agent")
public class AgentInfoController {

    @Autowired
    ServiceProvider serviceProvider;
    @Autowired
    AgentValidatorService validatorService;

    @RequestMapping(value="/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<AgentInfo>> getAgentInfo(@RequestBody AgentInfoQuery query) {
        return serviceProvider.getAgentInfoService().getPage(query);
    }

    @RequestMapping(value="/search/source", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Map<String, Object>> searchSource() {
        Map<String, Object> source = new HashMap<>();
        List<OptionItemVo> agentInfos = serviceProvider.getAgentInfoService().getAgentInfos();
        List<OptionItemVo> agentCertInfos = serviceProvider.getAgentInfoService().getAgentCorporationInfos();
        source.put("agent_name_list", agentInfos);
        source.put("agent_corp_list", agentCertInfos);
        List<OptionItemVo> collectStatus = new ArrayList<>();
        collectStatus.add(OptionItemVo.builder().text("全部").value("").build());
        collectStatus.addAll(EnumSet.allOf(StatusType.class).stream().map(e -> OptionItemVo.builder().value(e.name()).text(e.getDesc()).build()).collect(Collectors.toList()));
        source.put("status_list", collectStatus);
        return ResponseResult.<Map<String, Object>>builder().data(source).build().forOk();
    }


    @RequestMapping("/todoedit")
    @ResponseBody
    public ResponseResult<Map<String, Object>> todoEdit(@RequestParam(value = "agentId", required = false) Long agentId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> source = new HashMap<>();
        AgentInfo agentInfo = null;
        if (Objects.nonNull(agentId)) {
            agentInfo = serviceProvider.getAgentInfoService().getAgentInfo(AgentInfo.builder().id(agentId).build());
        }
        source.put("certificate_type", EnumSet.allOf(QualificationType.class).stream().map(e->OptionItemVo.builder().value(e.getIndex()).text(e.getDesc()).build()).collect(Collectors.toList()));
        result.put("source", source);
        result.put("info", agentInfo);
        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }


    @RequestMapping(value="/doedit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doEdit(@RequestBody AgentInfo agentInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        preOperator(agentInfo);
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.UPDATE, agentInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        agentInfo.setCurrentUserId(cuid);
        Map<String, Object> result = new HashMap<>();
        serviceProvider.getAgentInfoService().edit(agentInfo);
        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }


    @RequestMapping(value="/doadd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Map<String, Object>> doAdd(@RequestBody AgentInfo agentInfo, @RequestParam(value = "cuid", required = false) Long cuid) {
        preOperator(agentInfo);
        ResponseResult<Map<String, Object>> stringResponseResult = validatorService.fromValidator(ValidType.ADD, agentInfo);
        if (stringResponseResult.isFail()) {
            return stringResponseResult;
        }
        agentInfo.setCurrentUserId(cuid);
        Map<String, Object> result = new HashMap<>();
        serviceProvider.getAgentInfoService().add(agentInfo);
        return ResponseResult.<Map<String, Object>>builder().data(result).build().forOk();
    }


    public void preOperator(AgentInfo agentInfo) {
        if(StringUtils.isNoneBlank(agentInfo.getQualificationType())){
            agentInfo.setQualificationType(String.valueOf(5001091));
        }
        if(Objects.isNull(agentInfo.getGiftBalance())){
        	agentInfo.setGiftBalance(0.0D);
        }
    }

}
