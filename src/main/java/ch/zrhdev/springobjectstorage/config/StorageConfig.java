package ch.zrhdev.springobjectstorage.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageResourceLoader;
import org.springframework.content.s3.config.EnableS3Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableS3Stores
public class StorageConfig {

    private static final Logger logger = LoggerFactory.getLogger(StorageConfig.class);

    @Value("${spring.content.s3.endpoint-url}")
    private String endpointUrl;
    @Value("${spring.content.s3.bucket}")
    private String bucketName;
    @Value("${spring.content.s3.access-key}")
    private String accessKey;
    @Value("${spring.content.s3.secret-key}")
    private String secretKey;

    @PostConstruct
    @Bean(name="s3client")
    public AmazonS3 client() {

        ClientConfiguration cfg = new ClientConfiguration();
        cfg.setSignerOverride("S3SignerType");

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, Regions.DEFAULT_REGION.getName()))
                .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials(accessKey, secretKey)))
                .withClientConfiguration(cfg)
                .build();

        logger.info("AWS Client Configuration: endpointPrefix={}", amazonS3);
        return amazonS3;
    }

    @Bean
    public SimpleStorageResourceLoader simpleStorageResourceLoader(AmazonS3 client) {
        return new SimpleStorageResourceLoader(client);
    }

}
