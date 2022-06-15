package it.pagopa.pdv.person.connector.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import it.pagopa.pdv.person.connector.dao.converter.LocalDateConverter;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@DynamoDBDocument
public class DynamoDBCertifiedFieldOfLocalDate extends DynamoDBCertifiedField<LocalDate> {

    public DynamoDBCertifiedFieldOfLocalDate(CertifiableField<LocalDate> certifiableField) {
        super(certifiableField);
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    private LocalDate value;

}
