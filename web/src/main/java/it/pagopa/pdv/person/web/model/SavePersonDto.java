package it.pagopa.pdv.person.web.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Data
public class SavePersonDto {

  @Schema(ref = "NameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> name;

  @Schema(ref = "FamilyNameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> familyName;

  @Schema(ref = "EmailCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> email;

  @Schema(ref = "BirthDateCertifiableSchema")
  @Valid
  private CertifiableFieldResource<LocalDate> birthDate;

  @Schema(description = "${swagger.model.person.workContacts}")
  @Valid
  private Map<String, WorkContactResource> workContacts;

  @Schema(ref = "SpidCodeCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> spidCode;

  @Schema(ref = "PlaceOfBirthCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> placeOfBirth;

  @Schema(ref = "CountyOfBirthCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> countyOfBirth;

  @Schema(ref = "GenderCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> gender;

  @Schema(ref = "CompanyNameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> companyName;

  @Schema(ref = "RegisteredOfficeCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> registeredOffice;

  @Schema(ref = "IvaCodeCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> ivaCode;

  @Schema(ref = "IdCardCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> idCard;

  @Schema(ref = "MobilePhoneCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> mobilePhone;

  @Schema(ref = "AddressCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> address;

  @Schema(ref = "ExpirationDateCertifiableSchema")
  @Valid
  private CertifiableFieldResource<LocalDate> expirationDate;

  @Schema(ref = "DigitalAddressCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> digitalAddress;

  @Schema(ref = "DomicileAddressCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> domicileAddress;

  @Schema(ref = "DomicilePlaceCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> domicilePlace;

  @Schema(ref = "DomicilePostalCodeCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> domicilePostalCode;

  @Schema(ref = "DomicileProvinceCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> domicileProvince;

  @Schema(ref = "DomicileCountryCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> domicileCountry;

  @Schema(ref = "QualificationCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> qualification;

  @Schema(ref = "CommonNameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> commonName;

  @Schema(ref = "SurnameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> surname;

  @Schema(ref = "GivenNameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> givenName;

  @Schema(ref = "PreferredUsernameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> preferredUsername;

  @Schema(ref = "TitleCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> title;

  @Schema(ref = "UserCertificateCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> userCertificate;

  @Schema(ref = "EmployeeNumberCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> employeeNumber;

  @Schema(ref = "OrgUnitNameCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> orgUnitName;

  @Schema(ref = "PreferredLanguageCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> preferredLanguage;

  @Schema(ref = "CountryCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> country;

  @Schema(ref = "StateOrProvinceCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> stateOrProvince;

  @Schema(ref = "CityCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> city;

  @Schema(ref = "PostalCodeCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> postalCode;

  @Schema(ref = "StreetCertifiableSchema")
  @Valid
  private CertifiableFieldResource<String> street;
}
