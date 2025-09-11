package it.pagopa.pdv.person.connector.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class DummyPersonDetails implements PersonDetailsOperations {

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

  private Map<String, WorkContactOperations> workContacts;


  public DummyPersonDetails() {
    this.id = UUID.randomUUID().toString();
    this.fiscalCode = "fiscalCode";
    this.name = new DummyCertifiedField<>(String.class);
    this.familyName = new DummyCertifiedField<>(String.class);
    this.email = new DummyCertifiedField<>(String.class);
    this.birthDate = new DummyCertifiedField<>(LocalDate.class);
    this.spidCode = new DummyCertifiedField<>(String.class);
    this.placeOfBirth = new DummyCertifiedField<>(String.class);
    this.countyOfBirth = new DummyCertifiedField<>(String.class);
    this.gender = new DummyCertifiedField<>(String.class);
    this.companyName = new DummyCertifiedField<>(String.class);
    this.registeredOffice = new DummyCertifiedField<>(String.class);
    this.ivaCode = new DummyCertifiedField<>(String.class);
    this.idCard = new DummyCertifiedField<>(String.class);
    this.mobilePhone = new DummyCertifiedField<>(String.class);
    this.address = new DummyCertifiedField<>(String.class);
    this.expirationDate = new DummyCertifiedField<>(LocalDate.class);
    this.digitalAddress = new DummyCertifiedField<>(String.class);
    this.domicileAddress = new DummyCertifiedField<>(String.class);
    this.domicilePlace = new DummyCertifiedField<>(String.class);
    this.domicilePostalCode = new DummyCertifiedField<>(String.class);
    this.domicileProvince = new DummyCertifiedField<>(String.class);
    this.domicileCountry = new DummyCertifiedField<>(String.class);
    this.qualification = new DummyCertifiedField<>(String.class);
    this.commonName = new DummyCertifiedField<>(String.class);
    this.surname = new DummyCertifiedField<>(String.class);
    this.givenName = new DummyCertifiedField<>(String.class);
    this.preferredUsername = new DummyCertifiedField<>(String.class);
    this.title = new DummyCertifiedField<>(String.class);
    this.userCertificate = new DummyCertifiedField<>(String.class);
    this.employeeNumber = new DummyCertifiedField<>(String.class);
    this.orgUnitName = new DummyCertifiedField<>(String.class);
    this.preferredLanguage = new DummyCertifiedField<>(String.class);
    this.country = new DummyCertifiedField<>(String.class);
    this.stateOrProvince = new DummyCertifiedField<>(String.class);
    this.city = new DummyCertifiedField<>(String.class);
    this.postalCode = new DummyCertifiedField<>(String.class);
    this.street = new DummyCertifiedField<>(String.class);
    this.workContacts = Map.of("inst-1", new DummyWorkContact());
  }

  @Data
  public static class DummyWorkContact implements WorkContactOperations {
    private CertifiableField<String> email;
    private CertifiableField<String> mobilePhone;
    private CertifiableField<String> telephone;

    public DummyWorkContact() {

      this.email = new DummyCertifiedField<>(String.class);
      this.mobilePhone = new DummyCertifiedField<>(String.class);
      this.telephone = new DummyCertifiedField<>(String.class);
    }
  }
  @Data
  public static class DummyWorkContactNullValue implements WorkContactOperations {
    private CertifiableField<String> email;
    private CertifiableField<String> mobilePhone;
    private CertifiableField<String> telephone;

    public DummyWorkContactNullValue() {

      this.email = new DummyCertifiedField<>();
      this.mobilePhone = new DummyCertifiedField<>(String.class);
      this.telephone = new DummyCertifiedField<>(String.class);        }
  }

}
