package it.pagopa.pdv.person.connector.dao.model;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import it.pagopa.pdv.person.connector.dao.PersonConnectorImpl;
import it.pagopa.pdv.person.connector.dao.converter.DynamoDBTypeConvertedJson;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static it.pagopa.pdv.person.connector.dao.model.Status.ACTIVE;

@Data
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
@DynamoDBTable(tableName = PersonConnectorImpl.TABLE_NAME)
public class PersonDetails implements PersonDetailsOperations {

    public PersonDetails(String id) {
        this.id = id;
    }

    public PersonDetails(PersonDetailsOperations personDetails) {
        id = personDetails.getId();
        if (personDetails.getName() != null) {
            name = new DynamoDBCertifiedField<>(personDetails.getName());
        }
        if (personDetails.getFamilyName() != null) {
            familyName = new DynamoDBCertifiedField<>(personDetails.getFamilyName());
        }
        if (personDetails.getEmail() != null) {
            email = new DynamoDBCertifiedField<>(personDetails.getEmail());
        }
        if (personDetails.getBirthDate() != null) {
            birthDate = new DynamoDBCertifiedField<>(personDetails.getBirthDate());
        }
        if (personDetails.getWorkContacts() != null) {
            workContacts = new HashMap<>(personDetails.getWorkContacts().size());
            personDetails.getWorkContacts().forEach((s, wc) -> workContacts.put(s, new WorkContact(wc)));
        }
    }

    @DynamoDBHashKey(attributeName = "PK")
    private String id;

    @DynamoDBRangeKey(attributeName = "SK")
    public String getType() {
        return "Details";
    }

    public void setType(String type) {
        // intentionally left blank: SK is set by default
    }

    @DynamoDBAttribute
    @FieldNameConstants.Include
    @DynamoDBTypeConvertedEnum
    private Status status = ACTIVE;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = String.class)
    private DynamoDBCertifiedField<String> name;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = String.class)
    private DynamoDBCertifiedField<String> familyName;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = String.class)
    private DynamoDBCertifiedField<String> email;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = LocalDate.class)
    private DynamoDBCertifiedField<LocalDate> birthDate;


    @DynamoDBAttribute
    @FieldNameConstants.Include
    private Map<String, WorkContact> workContacts;


    @Data
    @NoArgsConstructor
    @DynamoDBDocument
    public static class WorkContact implements WorkContactOperations {

        public WorkContact(WorkContactOperations workContact) {
            if (workContact.getEmail() != null) {
                email = new DynamoDBCertifiedField<>(workContact.getEmail());
            }
        }

        @DynamoDBAttribute
        @DynamoDBTypeConvertedJson(targetType = String.class)
        private DynamoDBCertifiedField<String> email;

    }

}
