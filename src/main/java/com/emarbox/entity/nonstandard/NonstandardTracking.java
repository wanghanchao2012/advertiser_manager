package com.emarbox.entity.nonstandard;

import lombok.Data;

import java.util.Date;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Table(name = "nn_tracking")
public class NonstandardTracking {

  private Long id;
  private Long adId;
  private Long projectId;
  private Long channelId;
  private String trafficSource;
  private String trafficUrl;
  
  @JsonProperty("ad_status")
  private Integer adStatus;
  private Date createTime;
  private Date updateTime;

}
