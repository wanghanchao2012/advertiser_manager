package com.emarbox.dto.nonstandard;

import java.util.List;

import com.emarbox.dto.common.RequestParamVo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NonstandardInfoVo{

	@JsonProperty("ad_type")
	private String adType;

	@JsonProperty("project_id")
	private String projectId;

	@JsonProperty("channel_id")
	private String channelId;

	@JsonProperty("landingpage_url")
	private String landingpageUrl;

	@JsonProperty("landingpage_name")
	private String landingpageName;

	@JsonProperty("ad_data")
	private List<NonstandardDataQuery> adData;

	@JsonProperty("tracking_url")
	private List<String> trackingUrl;
}
