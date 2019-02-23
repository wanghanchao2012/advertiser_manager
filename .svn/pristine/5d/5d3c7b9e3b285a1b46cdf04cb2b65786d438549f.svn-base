package com.emarbox.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.common.tuple.Tuple3;
import com.emarbox.entity.AgentInfo;
import com.emarbox.enums.ValidType;

@Service
public class AgentValidatorService extends ValidationBaseService {

    public ResponseResult<Map<String, Object>> fromValidator(ValidType validType, AgentInfo agentInfo) {

        List<Tuple3<Boolean, String, ValidType>> errorMsgList = new ArrayList<>();
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getAgentName()), "代理商简称不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getCorporation()), "公司名称不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getEmail()), "邮箱不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getQualificationCode()), "资质编号不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getQualificationImg()), "资质图片不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getQualificationType()), "资质类型不能为空"));

        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getAgentName()) && agentInfo.getAgentName().length() < 40, "代理商简称不能超过40个字符"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getEmail()) && agentInfo.getEmail().length() < 40, "邮箱不能超过40个字符"));
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getCorporation()) && agentInfo.getCorporation().length() < 56, "公司名称不能超过56个字符"));
        if(Objects.nonNull(agentInfo.getLinkMan()))
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getLinkMan()) && agentInfo.getLinkMan().length() < 20, "联系人不能超过20个字符"));
        if(Objects.nonNull(agentInfo.getPhoneNumber()))
        errorMsgList.add(getTuple3(Objects.nonNull(agentInfo.getPhoneNumber()) && agentInfo.getPhoneNumber().length() < 20, "电话/手机不能超过20个字符"));

        ResponseResult<Map<String, Object>> stringResponseResult = new ResponseResult<>();
        getResponseResult(validType, errorMsgList, stringResponseResult);
        return stringResponseResult;
    }


}
