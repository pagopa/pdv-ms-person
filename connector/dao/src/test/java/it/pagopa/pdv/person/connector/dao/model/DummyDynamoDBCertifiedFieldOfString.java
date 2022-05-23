package it.pagopa.pdv.person.connector.dao.model;

import lombok.SneakyThrows;

public class DummyDynamoDBCertifiedFieldOfString extends DynamoDBCertifiedFieldOfString {

    @SneakyThrows
    public DummyDynamoDBCertifiedFieldOfString(Class<String> clazz) {
        setCertification("certification");
        setValue("value");
    }

}