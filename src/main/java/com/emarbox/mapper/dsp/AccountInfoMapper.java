package com.emarbox.mapper.dsp;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface AccountInfoMapper {
	@Select(value = "select ifnull(pay_available_amount,0) pay_available_amount from account_info where user_id = 1")
	public Map<String, String> getPayAvaiableAmount();
}
