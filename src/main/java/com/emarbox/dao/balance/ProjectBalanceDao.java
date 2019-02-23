package com.emarbox.dao.balance;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.entity.AdvertiserAccountInfo;
import com.emarbox.entity.AdvertiserInfo;
import com.emarbox.entity.balance.PigProjectAccountInfo;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.mapper.MapperDspProvider;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class ProjectBalanceDao {

	@Autowired
	MapperAuthProvider mapperAuthProvider;
	@Autowired
	MapperDspProvider mapperDspProvider;

	// 获取代理商账户的可用余额
	public Double getPayAvaiableAmount(Long advertiserId) {
		Example example = new Example(AdvertiserAccountInfo.class);
		example.createCriteria().andEqualTo("advertiserId", advertiserId);
		List<AdvertiserInfo> advertiserList = mapperAuthProvider.getAdvertiserInfoMapper().selectByExample(example);
		return CollectionUtils.isEmpty(advertiserList) ? 0.0D : advertiserList.get(0).getCashBalance();
	}

	public PigProjectAccountInfo getAccountInfo(PigProjectAccountInfo project) {
		List<PigProjectAccountInfo> agentAccountInfoList = this.getAccountInfoList(project);
		if (CollectionUtils.isNotEmpty(agentAccountInfoList)) {
			return agentAccountInfoList.get(0);
		}
		return null;
	}

	public List<PigProjectAccountInfo> getAccountInfoList(PigProjectAccountInfo project) {
		Example example = new Example(PigProjectAccountInfo.class);
		Criteria createCriteria = example.createCriteria();
		if (Objects.nonNull(project.getProjectId())) {
			createCriteria.andEqualTo("projectId", project.getProjectId());
		}
		return mapperAuthProvider.getPigProjectAccountInfoMapper().selectByExample(example);
	}
}
