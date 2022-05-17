package it.pagopa.pdv.person.connector.dao.model;

import lombok.SneakyThrows;

import java.time.LocalDate;

public class DummyDynamoDBCertifiedField<T> extends DynamoDBCertifiedField<T> {

    @SneakyThrows
    public DummyDynamoDBCertifiedField(Class<T> clazz) {
        setCertification("certification");
        if (String.class.isAssignableFrom(clazz)) {
            setValue((T) "value");
        } else if (LocalDate.class.isAssignableFrom(clazz)) {
            setValue((T) LocalDate.now());
        } else {
            throw new IllegalArgumentException();
        }
    }

}