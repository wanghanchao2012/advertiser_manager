package com.emarbox.entity.nonstandard;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;

/**
 * @author 作者
 * @version 创建时间：2019年1月21日 下午2:44:46 类说明
 */
@Data
@Table(name = "yxt_campaign")
public class AdvertiserCampaign {
	private Long id;

	// 活动名称
	private String campaignName;

	private Long accountId;

	private Long projectId;

	private String campaignType;

	private String productType;

	private Double dailyBudget;

	private String biddingType;

	private String campaignStatus;

	private String createUser;

	private Date createTime;

	private String updateUser;

	private Date updateTime;

}
