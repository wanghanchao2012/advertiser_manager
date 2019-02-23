package com.emarbox.mapper.auth;

import com.emarbox.common.BaseMapper;
import com.emarbox.entity.balance.PigAgentAccountInfo;

public interface PigAgentAccountInfoMapper extends BaseMapper<PigAgentAccountInfo>{

	public Integer rechargeBalance(PigAgentAccountInfo accountInfo);
	
}
