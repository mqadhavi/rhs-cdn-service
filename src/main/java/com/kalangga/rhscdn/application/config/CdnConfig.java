package com.kalangga.rhscdn.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class CdnConfig {

	@Value("${cdn.protocol}")
	private String protocol;

	@Value("${cdn.domain}")
	private String domain;

	@Value("${cdn.access.key}")
	private String accessKey;

	@Value("${cdn.secret.key}")
	private String secretKey;

	@Bean
	public AmazonS3 s3client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		ClientConfiguration clientConfig = new ClientConfiguration();
		if (protocol.equalsIgnoreCase(Protocol.HTTPS.name())) {
			clientConfig.setProtocol(Protocol.HTTPS);
		} else {
			clientConfig.setProtocol(Protocol.HTTP);
		}

		AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials));
		amazonS3ClientBuilder.setClientConfiguration(clientConfig);
		amazonS3ClientBuilder.setEndpointConfiguration(new EndpointConfiguration(domain, Regions.US_EAST_1.name()));
		return amazonS3ClientBuilder.build();
	}
}
