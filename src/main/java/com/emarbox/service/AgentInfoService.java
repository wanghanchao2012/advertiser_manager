package com.emarbox.service;

import com.emarbox.dto.AgentInfoQuery;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.entity.AgentInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AgentInfoService extends BaseInfoService {

    public ResponseResult<List<AgentInfo>> getPage(AgentInfoQuery query) {
        ResponseResult.ResponseResultBuilder<List<AgentInfo>> builder = ResponseResult.builder();
        Integer pageSize = daoProvider.getAgentInfoDao().getCountByPage(query);
        List<AgentInfo> listByPage = Collections.emptyList();
        if (pageSize.intValue() > 0) {
            query.getPage().setRecordCount(pageSize);
            listByPage = daoProvider.getAgentInfoDao().getListByPage(query);
        }
        builder.data(listByPage).page(query.getPage());
        return builder.build().forOk();
    }

    public List<OptionItemVo> getAgentInfosEdit() {
        List<OptionItemVo> optionItemVoList = Collections.emptyList();
        List<AgentInfo> agentInfos = daoProvider.getAgentInfoDao().getAgentInfoAll();
        if (CollectionUtils.isNotEmpty(agentInfos)) {
            optionItemVoList = agentInfos.stream().filter(e -> Objects.nonNull(e.getAgentName())).map(e -> OptionItemVo.builder().value(e.getId()).text(e.getAgentName()).realValue(e.getAgentName()).build().preAppendValue()).collect(Collectors.toList());
        }
        return optionItemVoList;
    }

    public List<OptionItemVo> getAgentInfos() {
        return preAppendAll(getAgentInfosEdit());
    }

    public List<OptionItemVo> getAgentCorporationInfosEdit() {
        List<OptionItemVo> optionItemVoList = Collections.emptyList();
        List<AgentInfo> agentInfos = daoProvider.getAgentInfoDao().getAgentInfoAll();
        if (CollectionUtils.isNotEmpty(agentInfos)) {
            optionItemVoList = agentInfos.stream().filter(e -> Objects.nonNull(e.getCorporation())).map(e -> OptionItemVo.builder().value(e.getCorporation()).text(e.getCorporation()).build()).collect(Collectors.toList());
        }
        return optionItemVoList;
    }

    public List<OptionItemVo> getAgentCorporationInfos() {
        return preAppendAll(getAgentCorporationInfosEdit());
    }

    public AgentInfo getAgentInfo(AgentInfo agentInfo) {
        return daoProvider.getAgentInfoDao().getAgentInfo(agentInfo);
    }

    @Transactional
    public boolean edit(AgentInfo record) {
        record.setUpdateTime(new Date());
        record.setUpdateUser(Objects.nonNull(record.getCurrentUserId())?record.getCurrentUserId().toString():null);
        return daoProvider.getAgentInfoDao().edit(record);
    }

    @Transactional
    public boolean add(AgentInfo record) {
        record.setOpenAccountTime(new Date());
        record.setCreateTime(new Date());
        record.setCreateUser(Objects.nonNull(record.getCurrentUserId())?record.getCurrentUserId().toString():null);
        return daoProvider.getAgentInfoDao().add(record);
    }


}
