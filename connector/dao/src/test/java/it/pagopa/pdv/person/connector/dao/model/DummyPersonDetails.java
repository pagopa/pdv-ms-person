package it.pagopa.pdv.person.connector.dao.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonDetails extends PersonDetails {

    public DummyPersonDetails() {
        setId(UUID.randomUUID().toString());
        setName(new DummyDynamoDBCertifiedField<>(String.class));
        setFamilyName(new DummyDynamoDBCertifiedField<>(String.class));
        setEmail(new DummyDynamoDBCertifiedField<>(String.class));
        setBirthDate(new DummyDynamoDBCertifiedField<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContact()));
    }


    public static class DummyWorkContact extends WorkContact {
        public DummyWorkContact() {
            setEmail(new DummyDynamoDBCertifiedField<>(String.class));
        }
    }

}
