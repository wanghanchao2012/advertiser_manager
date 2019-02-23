package com.emarbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emarbox.dto.AvaiableAccountInfoDto;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.enums.BalanceOperationType;

@Controller
@RequestMapping("/account/advertiser/balance")
public class AdvertiserBalanceController {

	@RequestMapping("/available")
	@ResponseBody
	public ResponseResult<AvaiableAccountInfoDto> availableBalance(Long agentId) {
		return ResponseResult.<AvaiableAccountInfoDto> builder().build().forOk();
	}

	@RequestMapping(value="/operator/{rechargeType}",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> recharge(@PathVariable("rechargeType") BalanceOperationType rechargeType, Long agentId, Double balance) {
		return new ResponseResult<String>(0,"操作成功，已经送审.");
	}
}
