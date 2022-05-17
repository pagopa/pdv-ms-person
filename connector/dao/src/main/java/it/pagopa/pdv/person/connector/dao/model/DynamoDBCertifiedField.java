package it.pagopa.pdv.person.connector.dao.model;

import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
public class DynamoDBCertifiedField<T> implements CertifiableField<T> {

    public DynamoDBCertifiedField(CertifiableField<T> certifiableField) {
        Assert.notNull(certifiableField, "A CertifiableField is required");
        certification = certifiableField.getCertification();
        value = certifiableField.getValue();
    }

    private String certification;
    private T value;

}
