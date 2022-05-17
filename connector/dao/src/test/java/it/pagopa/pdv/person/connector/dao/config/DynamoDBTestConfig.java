package it.pagopa.pdv.person.connector.dao.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(DynamoDBConfig.class)
public class DynamoDBTestConfig {
}
