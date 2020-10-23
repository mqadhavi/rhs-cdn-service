package com.kalangga.rhscdn.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalangga.rhscdn.base.BaseResponse;
import com.kalangga.rhscdn.component.CdnComp;
import com.kalangga.rhscdn.payload.CdnRequest;
import com.kalangga.rhscdn.payload.CdnResponse;

@RestController
@RequestMapping("/api/v1/cdn")
public class CdnController {
	
	@Autowired
	CdnComp cdnComp;
	
	@Value("${cdn.protocol}")
	private String protocol;
	
	@Value("${cdn.domain}")
	private String domain;
	
	@PostMapping
	public ResponseEntity<?> upload(@RequestBody CdnRequest cdnRequest){
		
		String dir = UUID.randomUUID().toString();
		String path = cdnComp.upload(cdnRequest.getBucket().toLowerCase(), cdnRequest.getImage(), dir);
		BaseResponse<CdnResponse> response = new BaseResponse<CdnResponse>();
		if(null == path) {
			response.sendBadRequest(null, "Upload File Failed!");
			return ResponseEntity.badRequest().body(response);
		}
		CdnResponse cdnResponse = new CdnResponse();
		cdnResponse.setPath(path);
		cdnResponse.setBucket(cdnRequest.getBucket());
		cdnResponse.setBaseUrl(protocol+"://"+domain+"/");
		response.sendSuccess(cdnResponse, "Upload File Succeed!");
		if(response.getIsSuccess()) {
			return ResponseEntity.ok(response);	
		}
		return ResponseEntity.badRequest().body(response);
	}
	
	@DeleteMapping("/{bucket}/{path}")
	public ResponseEntity<?> delete(@PathVariable("bucket") String bucket, @PathVariable("path") String path) {
		BaseResponse response = new BaseResponse<>();
		try {
			cdnComp.delete(bucket,path);
			response.sendSuccess(null, "Delete File Succeed!");
			return ResponseEntity.ok(response);	
		} catch(Exception e){
			response.sendBadRequest(null, "Delete File Failed!");
			return ResponseEntity.badRequest().body(response);
		}
	}

}
