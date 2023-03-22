package it.pagopa.pdv.person.connector.dao.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
class DynamoDBConfig {

    @Configuration
    @Profile("!dev-local")
    static class Cloud {

        @Bean
        public AmazonDynamoDB amazonDynamoDB() {
            return AmazonDynamoDBClientBuilder
                    .standard()
                    .build();
        }


        @Bean
        public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
            return new DynamoDBMapper(amazonDynamoDB);
        }


        @Bean
        public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
            return new DynamoDB(amazonDynamoDB);
        }

    }


    @Configuration
    @Profile("dev-local")
    @PropertySource("classpath:config/dao-config.properties")
    static class DevLocal {

        @Value("${amazon.access.key}")
        private String accessKey;

        @Value("${amazon.access.secretkey}")
        private String secretKey;

        @Value("${amazon.region}")
        private String region;

        @Value("${dynamodb.endpoint.url}")
        private String dynamoDBEndpoint;


        @Bean
        public AmazonDynamoDB amazonDynamoDB() {
            return AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, region))
                    .build();
        }


        @Bean
        public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            return dynamoDBMapper;
        }


        @Bean
        public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
            return new DynamoDB(amazonDynamoDB);
        }

    }

}
