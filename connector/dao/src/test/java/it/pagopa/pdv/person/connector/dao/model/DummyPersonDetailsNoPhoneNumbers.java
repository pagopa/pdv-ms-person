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
    setWorkContacts(Map.of("inst-1", new DummyWorkContactNoPhoneNumbers()));
  }


  public static class DummyWorkContactNoPhoneNumbers extends WorkContact {
    public DummyWorkContactNoPhoneNumbers() {
      setEmail(new DummyDynamoDBCertifiedFieldOfString(String.class));
    }
  }

}
