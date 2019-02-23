package com.emarbox.entity.nonstandard;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Table(name = "nn_ad")
public class NonstandardAd {

	private Long id;
	@JsonProperty("landingpage_name")
	private String landingpageName;

	@JsonProperty("landingpage_url")
	private String landingpageUrl;

	@Transient
	@JsonProperty("view_url")
	private String viewUrl;

	@JsonProperty("ad_type")
	private Integer adType;

	@JsonProperty("ad_status")
	private Integer adStatus;

	@JsonProperty("project_id")
	private Long projectId;

	@JsonProperty("channel_id")
	private Long channelId;

	private Date createTime;
	private Date updateTime;

}
