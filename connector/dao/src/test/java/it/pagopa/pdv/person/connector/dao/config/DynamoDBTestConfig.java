package it.pagopa.pdv.person.connector.dao.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.pagopa.pdv.person.connector.dao.PersonConnectorImpl;
import it.pagopa.pdv.person.connector.dao.model.PersonDetails;
import it.pagopa.pdv.person.connector.dao.model.PersonId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

@TestConfiguration
@Import(DynamoDBConfig.class)
public class DynamoDBTestConfig {

    public static void dynamoDBLocalSetup(AmazonDynamoDB client, DynamoDBMapper dynamoDBMapper) {
        ListTablesResult tablesResult = client.listTables();
        if (!tablesResult.getTableNames().contains(PersonConnectorImpl.TABLE_NAME)) {
            CreateTableRequest tableRequest = createCreateTableRequest(dynamoDBMapper);
            client.createTable(tableRequest);
        }
    }
    private static CreateTableRequest createCreateTableRequest(DynamoDBMapper dynamoDBMapper){
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(PersonDetails.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

        CreateTableRequest tableRequestNamespaced = dynamoDBMapper.generateCreateTableRequest(PersonId.class);
        if (tableRequest.getGlobalSecondaryIndexes() == null) {
            tableRequest.setGlobalSecondaryIndexes(tableRequestNamespaced.getGlobalSecondaryIndexes());
        } else {
            tableRequest.getGlobalSecondaryIndexes().addAll(tableRequestNamespaced.getGlobalSecondaryIndexes());
        }
        if (tableRequest.getAttributeDefinitions() == null) {
            tableRequest.setAttributeDefinitions(tableRequestNamespaced.getAttributeDefinitions());
        } else {
            tableRequest.getAttributeDefinitions().addAll(tableRequestNamespaced.getAttributeDefinitions());
        }
        tableRequest.setAttributeDefinitions(new HashSet<>(tableRequest.getAttributeDefinitions()));

        if (tableRequest.getGlobalSecondaryIndexes() != null) {
            tableRequest.getGlobalSecondaryIndexes().forEach(gsi -> {
                gsi.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
                gsi.getProjection().setProjectionType(ProjectionType.KEYS_ONLY);
            });
        }
        return tableRequest;
    }
    @Test
    void generateCreateTableRequest() throws IOException {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(null);
        CreateTableRequest tableRequest = createCreateTableRequest(dynamoDBMapper);
        Path basePath = Paths.get("target/test/dynamodb-local-table-template/");
        Files.createDirectories(basePath);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String formatted = objectMapper.writeValueAsString(tableRequest);
        JsonNode jsonNode = objectMapper.readTree(formatted);
        ((ObjectNode) jsonNode).remove("RequestClientOptions");
        ((ObjectNode) jsonNode).remove("GeneralProgressListener");
        ((ObjectNode) jsonNode).remove("ReadLimit");
        Files.write(basePath.resolve("Person.json"), jsonNode.toPrettyString().getBytes());
    }

}
