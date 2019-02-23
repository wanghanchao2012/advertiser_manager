package com.emarbox.dto.nonstandard;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NonstandardDataQuery {

	@JsonProperty("img_url")
	private String imgUrl;
	private String title;
	private String describe;
	@JsonProperty("click_url")
	private String clickUrl;
}
