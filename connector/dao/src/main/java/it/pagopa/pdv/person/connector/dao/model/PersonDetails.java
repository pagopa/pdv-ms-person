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
    if (personDetails.getSpidCode() != null) {
      spidCode = new DynamoDBCertifiedFieldOfString(personDetails.getSpidCode());
    }
    if (personDetails.getPlaceOfBirth() != null) {
      placeOfBirth = new DynamoDBCertifiedFieldOfString(personDetails.getPlaceOfBirth());
    }
    if (personDetails.getCountyOfBirth() != null) {
      countyOfBirth = new DynamoDBCertifiedFieldOfString(personDetails.getCountyOfBirth());
    }
    if (personDetails.getGender() != null) {
      gender = new DynamoDBCertifiedFieldOfString(personDetails.getGender());
    }
    if (personDetails.getCompanyName() != null) {
      companyName = new DynamoDBCertifiedFieldOfString(personDetails.getCompanyName());
    }
    if (personDetails.getRegisteredOffice() != null) {
      registeredOffice = new DynamoDBCertifiedFieldOfString(personDetails.getRegisteredOffice());
    }
    if (personDetails.getIvaCode() != null) {
      ivaCode = new DynamoDBCertifiedFieldOfString(personDetails.getIvaCode());
    }
    if (personDetails.getIdCard() != null) {
      idCard = new DynamoDBCertifiedFieldOfString(personDetails.getIdCard());
    }
    if (personDetails.getMobilePhone() != null) {
      mobilePhone = new DynamoDBCertifiedFieldOfString(personDetails.getMobilePhone());
    }
    if (personDetails.getAddress() != null) {
      address = new DynamoDBCertifiedFieldOfString(personDetails.getAddress());
    }
    if (personDetails.getExpirationDate() != null) {
      expirationDate = new DynamoDBCertifiedFieldOfLocalDate(personDetails.getExpirationDate());
    }
    if (personDetails.getDigitalAddress() != null) {
      digitalAddress = new DynamoDBCertifiedFieldOfString(personDetails.getDigitalAddress());
    }
    if (personDetails.getDomicileAddress() != null) {
      domicileAddress = new DynamoDBCertifiedFieldOfString(personDetails.getDomicileAddress());
    }
    if (personDetails.getDomicilePlace() != null) {
      domicilePlace = new DynamoDBCertifiedFieldOfString(personDetails.getDomicilePlace());
    }
    if (personDetails.getDomicilePostalCode() != null) {
      domicilePostalCode = new DynamoDBCertifiedFieldOfString(
          personDetails.getDomicilePostalCode());
    }
    if (personDetails.getDomicileProvince() != null) {
      domicileProvince = new DynamoDBCertifiedFieldOfString(personDetails.getDomicileProvince());
    }
    if (personDetails.getDomicileCountry() != null) {
      domicileCountry = new DynamoDBCertifiedFieldOfString(personDetails.getDomicileCountry());
    }
    if (personDetails.getQualification() != null) {
      qualification = new DynamoDBCertifiedFieldOfString(personDetails.getQualification());
    }
    if (personDetails.getCommonName() != null) {
      commonName = new DynamoDBCertifiedFieldOfString(personDetails.getCommonName());
    }
    if (personDetails.getSurname() != null) {
      surname = new DynamoDBCertifiedFieldOfString(personDetails.getSurname());
    }
    if (personDetails.getGivenName() != null) {
      givenName = new DynamoDBCertifiedFieldOfString(personDetails.getGivenName());
    }
    if (personDetails.getPreferredUsername() != null) {
      preferredUsername = new DynamoDBCertifiedFieldOfString(personDetails.getPreferredUsername());
    }
    if (personDetails.getTitle() != null) {
      title = new DynamoDBCertifiedFieldOfString(personDetails.getTitle());
    }
    if (personDetails.getUserCertificate() != null) {
      userCertificate = new DynamoDBCertifiedFieldOfString(personDetails.getUserCertificate());
    }
    if (personDetails.getEmployeeNumber() != null) {
      employeeNumber = new DynamoDBCertifiedFieldOfString(personDetails.getEmployeeNumber());
    }
    if (personDetails.getOrgUnitName() != null) {
      orgUnitName = new DynamoDBCertifiedFieldOfString(personDetails.getOrgUnitName());
    }
    if (personDetails.getPreferredLanguage() != null) {
      preferredLanguage = new DynamoDBCertifiedFieldOfString(personDetails.getPreferredLanguage());
    }
    if (personDetails.getCountry() != null) {
      country = new DynamoDBCertifiedFieldOfString(personDetails.getCountry());
    }
    if (personDetails.getStateOrProvince() != null) {
      stateOrProvince = new DynamoDBCertifiedFieldOfString(personDetails.getStateOrProvince());
    }
    if (personDetails.getCity() != null) {
      city = new DynamoDBCertifiedFieldOfString(personDetails.getCity());
    }
    if (personDetails.getPostalCode() != null) {
      postalCode = new DynamoDBCertifiedFieldOfString(personDetails.getPostalCode());
    }
    if (personDetails.getStreet() != null) {
      street = new DynamoDBCertifiedFieldOfString(personDetails.getStreet());
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
  private DynamoDBCertifiedFieldOfString spidCode;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString placeOfBirth;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString countyOfBirth;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString gender;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString companyName;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString registeredOffice;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString ivaCode;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString idCard;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString mobilePhone;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString address;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfLocalDate expirationDate;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString digitalAddress;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString domicileAddress;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString domicilePlace;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString domicilePostalCode;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString domicileProvince;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString domicileCountry;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString qualification;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString commonName;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString surname;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString givenName;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString preferredUsername;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString title;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString userCertificate;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString employeeNumber;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString orgUnitName;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString preferredLanguage;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString country;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString stateOrProvince;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString city;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString postalCode;

  @DynamoDBAttribute
  private DynamoDBCertifiedFieldOfString street;


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
      if (workContact.getMobilePhone() != null) {
        mobilePhone = new DynamoDBCertifiedFieldOfString(workContact.getMobilePhone());
      }
      if (workContact.getTelephone() != null) {
        telephone = new DynamoDBCertifiedFieldOfString(workContact.getTelephone());
      }
    }

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfString email;

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfString mobilePhone;

    @DynamoDBAttribute
    private DynamoDBCertifiedFieldOfString telephone;

  }

}
