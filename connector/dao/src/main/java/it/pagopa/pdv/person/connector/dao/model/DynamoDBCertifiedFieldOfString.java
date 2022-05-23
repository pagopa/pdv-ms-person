package it.pagopa.pdv.person.connector.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@DynamoDBDocument
public class DynamoDBCertifiedFieldOfString extends DynamoDBCertifiedField<String> {

    public DynamoDBCertifiedFieldOfString(CertifiableField<String> certifiableField) {
        super(certifiableField);
    }

    @DynamoDBAttribute
    private String value;

}
