package com.emarbox.mapper.auth;

import com.emarbox.common.BaseMapper;
import com.emarbox.entity.AdvertiserAccountInfo;

public interface PigAdvertiserAccountInfoMapper extends BaseMapper<AdvertiserAccountInfo>{
	
	public Integer rechargeBalance(AdvertiserAccountInfo accountInfo);

}
