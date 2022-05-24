package it.pagopa.pdv.person.connector.dao.model;

import lombok.SneakyThrows;

import java.time.LocalDate;

public class DummyDynamoDBCertifiedFieldOfLocalDate extends DynamoDBCertifiedFieldOfLocalDate {

    @SneakyThrows
    public DummyDynamoDBCertifiedFieldOfLocalDate(Class<LocalDate> clazz) {
        setCertification("certification");
        setValue(LocalDate.now());
    }

}