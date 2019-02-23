package com.emarbox.mapper.auth;

import com.emarbox.common.BaseMapper;
import com.emarbox.dto.AgentInfoQuery;
import com.emarbox.entity.AgentInfo;

import java.util.List;

public interface AgentInfoMapper extends BaseMapper<AgentInfo> {

    public List<AgentInfo> getListByPage(AgentInfoQuery query);

    public Integer getCountByPage(AgentInfoQuery query);
}
