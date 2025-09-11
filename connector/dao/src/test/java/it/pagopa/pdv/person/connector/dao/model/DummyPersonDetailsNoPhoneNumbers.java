package it.pagopa.pdv.person.connector.dao.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonDetailsNoPhoneNumbers extends PersonDetails {

  public DummyPersonDetailsNoPhoneNumbers() {
    setId(UUID.randomUUID().toString());
    setName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setFamilyName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setEmail(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setBirthDate(new DummyDynamoDBCertifiedFieldOfLocalDate(LocalDate.class));
    setSpidCode(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setMobilePhone(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setDigitalAddress(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setAddress(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setIvaCode(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setCompanyName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setRegisteredOffice(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setIdCard(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setExpirationDate(new DummyDynamoDBCertifiedFieldOfLocalDate(LocalDate.class));
    setPlaceOfBirth(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setCountyOfBirth(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setGender(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setQualification(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setCommonName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setSurname(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setGivenName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setPreferredUsername(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setTitle(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setUserCertificate(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setEmployeeNumber(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setOrgUnitName(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setPreferredLanguage(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setCountry(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setStateOrProvince(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setCity(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setPostalCode(new DummyDynamoDBCertifiedFieldOfString(String.class));
    setStreet(new DummyDynamoDBCertifiedFieldOfString(String.class));

    setWorkContacts(Map.of("inst-1", new DummyWorkContactNoPhoneNumbers()));
  }


  public static class DummyWorkContactNoPhoneNumbers extends WorkContact {
    public DummyWorkContactNoPhoneNumbers() {
      setEmail(new DummyDynamoDBCertifiedFieldOfString(String.class));
    }
  }

}
