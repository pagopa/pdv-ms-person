package it.pagopa.pdv.person.connector.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import it.pagopa.pdv.person.connector.dao.PersonConnectorImpl;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
@DynamoDBTable(tableName = PersonConnectorImpl.TABLE_NAME)
public class PersonId implements PersonIdOperations {

    public PersonId(PersonIdOperations personId) {
        globalId = personId.getGlobalId();
        namespace = personId.getNamespace();
        namespacedId = personId.getNamespacedId();
    }


    @DynamoDBHashKey(attributeName = "PK")
    private String globalId;

    @DynamoDBRangeKey(attributeName = "SK")
    public String getSK() {
        return "Namespace#" + namespace;
    }

    public void setSK(String type) {
        // intentionally left blank: SK is set by setting namespace attribute
    }

    @DynamoDBAttribute
    private String namespace;

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "gsi_namespaced_id")
    @FieldNameConstants.Include
    private String namespacedId;

}
