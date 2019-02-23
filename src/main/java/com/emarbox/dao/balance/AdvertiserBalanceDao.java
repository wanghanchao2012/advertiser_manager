package com.emarbox.dao.balance;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.entity.AdvertiserAccountInfo;
import com.emarbox.entity.AgentInfo;
import com.emarbox.entity.balance.PigAgentAccountInfo;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.mapper.MapperDspProvider;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class AdvertiserBalanceDao {

	@Autowired
	MapperAuthProvider mapperAuthProvider;
	@Autowired
	MapperDspProvider mapperDspProvider;

	// 获取代理商账户的可用余额
	public Double getPayAvaiableAmount(Long agentId) {
		Example example = new Example(PigAgentAccountInfo.class);
		example.createCriteria().andEqualTo("agentId", agentId);
		List<AgentInfo> payAvaiableAmount = mapperAuthProvider.getAgentInfoMapper().selectByExample(example);
		return CollectionUtils.isEmpty(payAvaiableAmount) ? 0.0D : payAvaiableAmount.get(0).getCashBalance();
	}

	public AdvertiserAccountInfo getAccountInfo(PigAgentAccountInfo account) {
		List<AdvertiserAccountInfo> agentAccountInfoList = this.getAccountInfoList(account);
		if (CollectionUtils.isNotEmpty(agentAccountInfoList)) {
			return agentAccountInfoList.get(0);
		}
		return null;
	}

	public List<AdvertiserAccountInfo> getAccountInfoList(PigAgentAccountInfo account) {
		Example example = new Example(PigAgentAccountInfo.class);
		Criteria createCriteria = example.createCriteria();
		if (Objects.nonNull(account.getAgentId())) {
			createCriteria.andEqualTo("agentId", account.getAgentId());
		}
		return mapperAuthProvider.getPigAdvertiserAccountInfoMapper().selectByExample(example);
	}
}
