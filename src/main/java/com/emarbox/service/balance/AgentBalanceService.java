package com.emarbox.service.balance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emarbox.dao.balance.AgentBalanceDao;
import com.emarbox.dto.AvaiableAccountInfoDto;
import com.emarbox.entity.balance.PigAgentAccountInfo;
import com.emarbox.entity.balance.PigAgentDepositLog;
import com.emarbox.entity.balance.PigAgentPresentLog;

@Service
public class AgentBalanceService {
	@Autowired
	AgentBalanceDao agentBalanceDao;

	public AvaiableAccountInfoDto getAvaiableAmount(Long agentId) {
		Double payAvaiableAmount = agentBalanceDao.getPayAvaiableAmount();
		PigAgentAccountInfo account = new PigAgentAccountInfo();
		account.setAgentId(agentId);
		PigAgentAccountInfo accountInfo = agentBalanceDao.getAccountInfo(account);
		AvaiableAccountInfoDto build = AvaiableAccountInfoDto.builder().avaiableAmount(payAvaiableAmount).avaiableAmount(accountInfo.getCashBalance()).corporateName(accountInfo.getCorporateName()).build();
		return build;
	}
	
	public boolean rechargeDeposit(){
		return false;
	}
	
	public boolean rechargePresent(){
		return false;
	}

	@Transactional
	private void rechargeDepositToDb(Long agentId,Double rechargeAmount,String customerName){
		PigAgentAccountInfo accountInfo = PigAgentAccountInfo.builder().agentId(agentId).cashBalance(rechargeAmount).build();
		agentBalanceDao.updateAccountAvaiableAmount(accountInfo);
		PigAgentDepositLog depositLog = PigAgentDepositLog.builder().agentId(agentId).depositAmount(rechargeAmount).depositTime(new Date()).customerName(customerName).build();
		agentBalanceDao.insertDeposit(depositLog);
	}
	
	@Transactional
	private void rechargePresentToDb(Long agentId,Double rechargeAmount,String customerName){
		PigAgentAccountInfo accountInfo = PigAgentAccountInfo.builder().agentId(agentId).giftBalance(rechargeAmount).build();
		agentBalanceDao.updateAccountAvaiableAmount(accountInfo);
		PigAgentPresentLog presengLog = PigAgentPresentLog.builder().agentId(agentId).presentAmount(rechargeAmount).presentTime(new Date()).customerName(customerName).build();
		agentBalanceDao.insertPresent(presengLog);
	}
}