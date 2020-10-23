package com.kalangga.rhscdn.component;

import org.springframework.web.multipart.MultipartFile;

public interface CdnComp {
	
	void createBucket();

	String upload(String bucketName, MultipartFile file, String directory);

	String upload(String bucketName, String base64File, String directory);
	
	String uploadFile(String bucketName, String base64File, String directory);

	void delete(String bucketName, String objectName);
	
}
