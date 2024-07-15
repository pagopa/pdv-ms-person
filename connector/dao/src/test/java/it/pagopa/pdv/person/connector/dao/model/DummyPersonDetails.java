package it.pagopa.pdv.person.connector.dao.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonDetails extends PersonDetails {

    public DummyPersonDetails() {
        setId(UUID.randomUUID().toString());
        setName(new DummyDynamoDBCertifiedFieldOfString(String.class));
        setFamilyName(new DummyDynamoDBCertifiedFieldOfString(String.class));
        setEmail(new DummyDynamoDBCertifiedFieldOfString(String.class));
        setBirthDate(new DummyDynamoDBCertifiedFieldOfLocalDate(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContact()));
    }


    public static class DummyWorkContact extends WorkContact {
        public DummyWorkContact() {
            setEmail(new DummyDynamoDBCertifiedFieldOfString(String.class));
            setPhone(new DummyDynamoDBCertifiedFieldOfString(String.class));
        }
    }

}
