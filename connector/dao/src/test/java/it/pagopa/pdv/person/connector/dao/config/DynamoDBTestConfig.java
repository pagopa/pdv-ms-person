package it.pagopa.pdv.person.connector.dao.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import it.pagopa.pdv.person.connector.dao.PersonConnectorImpl;
import it.pagopa.pdv.person.connector.dao.model.PersonDetails;
import it.pagopa.pdv.person.connector.dao.model.PersonId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import java.util.HashSet;

@TestConfiguration
@Import(DynamoDBConfig.class)
public class DynamoDBTestConfig {

    public static void dynamoDBLocalSetup(AmazonDynamoDB client, DynamoDBMapper dynamoDBMapper) {
        ListTablesResult tablesResult = client.listTables();
        if (!tablesResult.getTableNames().contains(PersonConnectorImpl.TABLE_NAME)) {
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

            client.createTable(tableRequest);
        }
    }

}
