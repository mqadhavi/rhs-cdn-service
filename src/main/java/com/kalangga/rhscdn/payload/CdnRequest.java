package com.kalangga.rhscdn.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CdnRequest {

	private String bucket;
	
	private String image;
}
