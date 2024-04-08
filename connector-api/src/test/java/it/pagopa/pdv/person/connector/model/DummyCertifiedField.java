package it.pagopa.pdv.person.connector.model;

import lombok.Data;
import lombok.SneakyThrows;

import java.time.LocalDate;

@Data
public class DummyCertifiedField<T> implements CertifiableField<T> {

    private String certification = "certification";
    private T value;


    @SneakyThrows
    public DummyCertifiedField(Class<T> clazz) {
        if (String.class.isAssignableFrom(clazz)) {
            value = (T) "value";
        } else if (LocalDate.class.isAssignableFrom(clazz)) {
            value = (T) LocalDate.now();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @SneakyThrows
    public DummyCertifiedField() {
        value = null;
    }

}