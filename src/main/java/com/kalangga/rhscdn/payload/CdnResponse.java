package com.kalangga.rhscdn.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CdnResponse {

	private String bucket;
	
	private String path;
	
	@JsonProperty("base_url")
	private String baseUrl;
	
}
