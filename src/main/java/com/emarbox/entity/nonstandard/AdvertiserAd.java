package com.emarbox.entity.nonstandard;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;

/**
 * @author 作者
 * @version 创建时间：2019年1月21日 下午2:44:46 类说明
 */
@Data
@Table(name = "yxt_ad")
public class AdvertiserAd {
	
    private  Long id;
   
	
    private  Long accountId;
   
	 //项目id活动同步时使用
    private  Long projectId;
   
	
    private  String productUrl;
   
	
    private  Date startTime;
   
	
    private  Date endTime;
   
	
    private  String scheduleTime;
   
	
    private  String feeType;
   
	
    private  String chargeType;
   
	
    private  String optimizationGoal;
   
	
    private  Double bidPrice;
   
	
    private  String adName;
   
	
    private  Long adId;
   
	
    private  Long campaignId;
   
	
    private  String adStatus;
   
	
    private  String targeting;
   
	
    private  String createUser;
   
	
    private  Date createTime;
   
	
    private  String updateUser;
   
	
    private  Date updateTime;
   
    

}
