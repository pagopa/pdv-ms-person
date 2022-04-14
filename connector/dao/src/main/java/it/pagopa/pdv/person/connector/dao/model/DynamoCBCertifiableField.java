package it.pagopa.pdv.person.connector.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
@DynamoDBTypeConvertedJson
public class DynamoCBCertifiableField<T> implements CertifiableField<T> {

    public DynamoCBCertifiableField(CertifiableField<T> certifiableField) {
        Assert.notNull(certifiableField, "A CertifiableField is required");
        certification = certifiableField.getCertification();
        value = certifiableField.getValue();
    }

    private String certification;
    private T value;

}
