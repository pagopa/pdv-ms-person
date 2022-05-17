package it.pagopa.pdv.person.connector.dao.model;

import java.util.UUID;

public class DummyPersonId extends PersonId {

    public DummyPersonId() {
        setGlobalId(UUID.randomUUID().toString());
        setNamespacedId(UUID.randomUUID().toString());
        setNamespace("selfcare");
    }
}
