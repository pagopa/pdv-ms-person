package it.pagopa.pdv.person.connector.dao.model;


import static it.pagopa.pdv.person.connector.dao.model.Status.ACTIVE;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import it.pagopa.pdv.person.connector.dao.PersonConnectorImpl;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

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
            name = new DynamoDBCertifiedFieldOfString(personDetails.getName());
        }
        if (personDetails.getFamilyName() != null) {
            familyName = new DynamoDBCertifiedFieldOfString(personDetails.getFamilyName());
        }
        if (personDetails.getEmail() != null) {
            email = new DynamoDBCertifiedFieldOfString(personDetails.getEmail());
        }
        if (personDetails.getBirthDate() != null) {
            birthDate = new DynamoDBCertifiedFieldOfLocalDate(personDetails.getBirthDate());
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
    private DynamoDBCertifiedFieldOfString name;

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfString familyName;

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfString email;

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfLocalDate birthDate;


    @DynamoDBAttribute
    @FieldNameConstants.Include
    private Map<String, WorkContact> workContacts;


    @Data
    @NoArgsConstructor
    @DynamoDBDocument
    public static class WorkContact implements WorkContactOperations {

        public WorkContact(WorkContactOperations workContact) {
            if (workContact.getEmail() != null) {
                email = new DynamoDBCertifiedFieldOfString(workContact.getEmail());
            }
            if (workContact.getPhone() != null) {
                phone = new DynamoDBCertifiedFieldOfString(workContact.getPhone());
            }
        }

        @DynamoDBAttribute
        private DynamoDBCertifiedFieldOfString email;

        @DynamoDBAttribute
        private DynamoDBCertifiedFieldOfString phone;

    }

}
