package com.emarbox.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.mapper.auth.AdvertiserInfoMapper;
import com.emarbox.mapper.auth.AgentInfoMapper;
import com.emarbox.mapper.auth.EmarboxProjectCategoryMapper;
import com.emarbox.mapper.auth.EmarboxProjectMapper;
import com.emarbox.mapper.auth.EmarboxProjectModuleMapper;
import com.emarbox.mapper.auth.EmarboxUserMapper;
import com.emarbox.mapper.auth.EmarboxUserProjectMapper;
import com.emarbox.mapper.auth.PigAdvertiserAccountInfoMapper;
import com.emarbox.mapper.auth.PigAdvertiserDepositLogMapper;
import com.emarbox.mapper.auth.PigAdvertiserPresentLogMapper;
import com.emarbox.mapper.auth.PigAdvertiserRefundLogMapper;
import com.emarbox.mapper.auth.PigAgentAccountInfoMapper;
import com.emarbox.mapper.auth.PigAgentDepositLogMapper;
import com.emarbox.mapper.auth.PigAgentPresentLogMapper;
import com.emarbox.mapper.auth.PigAgentRefundLogMapper;
import com.emarbox.mapper.auth.PigProjectAccountInfoMapper;
import com.emarbox.mapper.auth.PigProjectDepositLogMapper;
import com.emarbox.mapper.auth.PigProjectPresentLogMapper;
import com.emarbox.mapper.auth.PigProjectRefundLogMapper;
import com.emarbox.mapper.auth.PlatformIndustryInfoMapper;
import com.emarbox.mapper.auth.ProjectCertificateMapper;

import lombok.Data;

@Data
@Repository
public class MapperAuthProvider {
	@Autowired
	AdvertiserInfoMapper advertiserInfoMapper;
	@Autowired
	AgentInfoMapper agentInfoMapper;

	@Autowired
	PlatformIndustryInfoMapper platformIndustryInfoMapper;
	@Autowired
	EmarboxProjectCategoryMapper emarboxProjectCategoryMapper;
	@Autowired
	ProjectCertificateMapper projectCertificateMapper;

	/**
	 * compatibility with historical versions
	 */
	@Autowired
	EmarboxProjectMapper emarboxProjectMapper;
	@Autowired
	EmarboxUserMapper emarboxUserMapper;
	@Autowired
	EmarboxUserProjectMapper emarboxUserProjectMapper;
	@Autowired
	EmarboxProjectModuleMapper emarboxProjectModuleMapper;

	@Autowired
	PigProjectDepositLogMapper pigProjectDepositLogMapper;
	@Autowired
	PigProjectPresentLogMapper pigProjectPresentLogMapper;
	@Autowired
	PigProjectRefundLogMapper pigProjectRefundLogMapper;
	@Autowired
	PigProjectAccountInfoMapper pigProjectAccountInfoMapper;

	@Autowired
	PigAdvertiserDepositLogMapper pigAdvertiserDepositLogMapper;
	@Autowired
	PigAdvertiserPresentLogMapper pigAdvertiserPresentLogMapper;
	@Autowired
	PigAdvertiserRefundLogMapper pigAdvertiserRefundLogMapper;
	@Autowired
	PigAdvertiserAccountInfoMapper pigAdvertiserAccountInfoMapper;

	@Autowired
	PigAgentDepositLogMapper pigAgentDepositLogMapper;
	@Autowired
	PigAgentPresentLogMapper pigAgentPresentLogMapper;
	@Autowired
	PigAgentRefundLogMapper pigAgentRefundLogMapper;
	@Autowired
	PigAgentAccountInfoMapper pigAgentAccountInfoMapper;

}
