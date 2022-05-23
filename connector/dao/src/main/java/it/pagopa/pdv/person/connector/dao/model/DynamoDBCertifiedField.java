package it.pagopa.pdv.person.connector.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
@FieldNameConstants
@DynamoDBDocument
public abstract class DynamoDBCertifiedField<T> implements CertifiableField<T> {

    public DynamoDBCertifiedField(CertifiableField<T> certifiableField) {
        Assert.notNull(certifiableField, "A CertifiableField is required");
        certification = certifiableField.getCertification();
        setValue(certifiableField.getValue());
    }

    @DynamoDBAttribute
    private String certification;

}
