package com.kalangga.rhscdn.component;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kalangga.rhscdn.util.ImageUtil;
import com.kalangga.rhscdn.util.StringUtil;


@Component
public class CdCompImpl implements CdnComp {
	
	Logger log = LoggerFactory.getLogger(CdCompImpl.class);
	
	@Value("${cdn.bucket.name}")
	private String cdnBucketName;

	@Autowired
	private AmazonS3 amazoneS3;

	@Value("${cdn.domain}")
	private String domain;

	private final long MAX_SIZE = 2_000_000;
	
	private final long FILE_MAX_SIZE = 5_000_000;

	@Override
	public void createBucket() {
		try {
			boolean exist = amazoneS3.doesBucketExistV2(cdnBucketName);
			if (!exist) {
				log.info("Create bucket : {}", cdnBucketName);
				amazoneS3.createBucket(cdnBucketName);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public String upload(String bucketName, MultipartFile file, String directory) {
		String bucket = cdnBucketName;
		try {
			if (file.getSize() > MAX_SIZE) {
				return null;
			}
			createBucket();
			String extension = StringUtil.getExtension(file.getOriginalFilename());
			String objectName = directory + "." + extension;

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(ContentType.MULTIPART_FORM_DATA.getMimeType());
			PutObjectRequest request = new PutObjectRequest(bucket, objectName, file.getInputStream(), metadata);
			request.setCannedAcl(CannedAccessControlList.PublicRead);
			log.info("Upload : Bucket {}, Object {}", bucket, objectName);
			amazoneS3.putObject(request);
			return objectName;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String upload(String bucketName, String base64File, String directory) {
		String bucket = cdnBucketName;
		if(null != base64File) {
			try {
				createBucket();
				String extension = ImageUtil.getMimeType(base64File);
				String objectName = bucketName+"/"+directory + "." + extension;

				byte[] fileStream = Base64.getDecoder().decode(base64File);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(ContentType.MULTIPART_FORM_DATA.getMimeType());
				metadata.setContentLength(fileStream.length);
				PutObjectRequest request = new PutObjectRequest(bucket, objectName, new ByteArrayInputStream(fileStream), metadata);
				request.setCannedAcl(CannedAccessControlList.PublicRead);
				log.info("Upload : Bucket {}, Object {}", bucket, objectName);
				amazoneS3.putObject(request);
				return objectName;
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}	
		}
		return null;
	}
	
	@Override
	public String uploadFile(String bucketName, String base64File, String directory) {
		String bucket = cdnBucketName;
		if(null != base64File) {
			try {
				createBucket();
//				String extension = ImageUtil.getMimeType(base64File);
				String objectName = bucketName+"/"+directory + ".pdf";

				byte[] fileStream = Base64.getDecoder().decode(base64File);
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(ContentType.MULTIPART_FORM_DATA.getMimeType());
				metadata.setContentLength(fileStream.length);
				PutObjectRequest request = new PutObjectRequest(bucket, objectName, new ByteArrayInputStream(fileStream), metadata);
				request.setCannedAcl(CannedAccessControlList.PublicRead);
				log.info("Upload : Bucket {}, Object {}", bucket, objectName);
				amazoneS3.putObject(request);
				return objectName;
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}	
		}
		return null;
	}

	@Override
	public void delete(String bucketName, String objectName) {
		objectName = bucketName+"/"+objectName;
		String bucket = cdnBucketName;
		try {
			if (objectName != null && !objectName.isEmpty() && amazoneS3.doesBucketExistV2(bucket)) {
				log.info("Delete : Bucket {}, Object {}", bucket, objectName);
				amazoneS3.deleteObject(bucket, objectName);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
