package com.emarbox.dao.balance;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.entity.AgentInfo;
import com.emarbox.entity.balance.PigAgentAccountInfo;
import com.emarbox.entity.balance.PigAgentDepositLog;
import com.emarbox.entity.balance.PigAgentPresentLog;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.mapper.MapperDspProvider;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Repository
public class AgentBalanceDao {

	@Autowired
	MapperAuthProvider mapperAuthProvider;
	@Autowired
	MapperDspProvider mapperDspProvider;
	
	//获取admin账户的总充值余额
	public Double getPayAvaiableAmount(){
		Map<String, String> payAvaiableAmount = mapperDspProvider.getAccountInfoMapper().getPayAvaiableAmount();
		return MapUtils.isEmpty(payAvaiableAmount) ? 0.0D : Double.parseDouble(payAvaiableAmount.get("pay_available_amount"));
	}
	public PigAgentAccountInfo getAccountInfo(PigAgentAccountInfo account){
		List<PigAgentAccountInfo> agentAccountInfoList = this.getAccountInfoList(account);
		if(CollectionUtils.isNotEmpty(agentAccountInfoList)){
			PigAgentAccountInfo pigAgentAccountInfo = agentAccountInfoList.get(0);
			AgentInfo selectByPrimaryKey = mapperAuthProvider.getAgentInfoMapper().selectByPrimaryKey(account.getAgentId());
			if(Objects.nonNull(selectByPrimaryKey)){
				pigAgentAccountInfo.setCorporateName(selectByPrimaryKey.getCorporation());
			}
		}
		return null;
	}
	
	private List<PigAgentAccountInfo> getAccountInfoList(PigAgentAccountInfo account){
		Example example = new Example(PigAgentAccountInfo.class);
		Criteria createCriteria = example.createCriteria();
		if(Objects.nonNull(account.getAgentId())){
			createCriteria.andEqualTo("agentId", account.getAgentId());
		}
		return mapperAuthProvider.getPigAgentAccountInfoMapper().selectByExample(example);
	}
	
	public boolean updateAccountAvaiableAmount(PigAgentAccountInfo accountInfo){
		return mapperAuthProvider.getPigAgentAccountInfoMapper().rechargeBalance(accountInfo)>0;
	}
	
	public boolean insertPresent(PigAgentPresentLog log) {
		log.setCreateTime(new Date());
		log.setUpdateTime(new Date());
		return mapperAuthProvider.getPigAgentPresentLogMapper().insert(log) > 0;
	}
	
	public boolean insertDeposit(PigAgentDepositLog log) {
		log.setCreateTime(new Date());
		log.setUpdateTime(new Date());
		return mapperAuthProvider.getPigAgentDepositLogMapper().insert(log) > 0;
	}
}
