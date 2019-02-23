package com.emarbox.entity.nonstandard;

import lombok.Data;

import java.util.Date;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Table(name="nn_ad_info")
public class NonstandardAdInfo {

  private Long id;
  
  @JsonProperty("ad_id")
  private Long adId;
  
  @JsonProperty("campaign_id")
  private Long campaignId;
  
  @JsonProperty("creative_id")
  private Long creativeId;
  
  @JsonProperty("ad_type")
  private Integer adType;
  
  @JsonProperty("img_url")
  private String imgUrl;
  
  @JsonProperty("click_url")
  private String clickUrl;
  
  @JsonProperty("imp_url")
  private String impUrl;
  private String title;
  
  @JsonProperty("describe")
  private String adDescribe;
  
  @JsonProperty("ad_status")
  private Integer adStatus;
  private Date createTime;
  private Date updateTime;


}
