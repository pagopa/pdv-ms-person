package it.pagopa.pdv.person.connector.model;

import java.time.LocalDate;
import java.util.Map;
import lombok.Data;

@Data
public class PersonDto implements PersonDetailsOperations {

  private String id;
  private String fiscalCode;
  private CertifiableField<String> name;
  private CertifiableField<String> familyName;
  private CertifiableField<String> email;
  private CertifiableField<LocalDate> birthDate;
  private CertifiableField<String> spidCode;
  private CertifiableField<String> placeOfBirth;
  private CertifiableField<String> countyOfBirth;
  private CertifiableField<String> gender;
  private CertifiableField<String> companyName;
  private CertifiableField<String> registeredOffice;
  private CertifiableField<String> ivaCode;
  private CertifiableField<String> idCard;
  private CertifiableField<String> mobilePhone;
  private CertifiableField<String> address;
  private CertifiableField<LocalDate> expirationDate;
  private CertifiableField<String> digitalAddress;
  private CertifiableField<String> domicileAddress;
  private CertifiableField<String> domicilePlace;
  private CertifiableField<String> domicilePostalCode;
  private CertifiableField<String> domicileProvince;
  private CertifiableField<String> domicileCountry;
  private CertifiableField<String> qualification;
  private CertifiableField<String> commonName;
  private CertifiableField<String> surname;
  private CertifiableField<String> givenName;
  private CertifiableField<String> preferredUsername;
  private CertifiableField<String> title;
  private CertifiableField<String> userCertificate;
  private CertifiableField<String> employeeNumber;
  private CertifiableField<String> orgUnitName;
  private CertifiableField<String> preferredLanguage;
  private CertifiableField<String> country;
  private CertifiableField<String> stateOrProvince;
  private CertifiableField<String> city;
  private CertifiableField<String> postalCode;
  private CertifiableField<String> street;
  private Map<String, WorkContactDto> workContacts;


  @Data
  public static class WorkContactDto implements WorkContactOperations {

    private CertifiableField<String> email;
    private CertifiableField<String> mobilePhone;
    private CertifiableField<String> telephone;
  }

}
