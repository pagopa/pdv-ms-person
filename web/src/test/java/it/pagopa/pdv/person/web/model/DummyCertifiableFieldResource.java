package it.pagopa.pdv.person.web.model;

import lombok.SneakyThrows;

import java.time.LocalDate;

public class DummyCertifiableFieldResource<T> extends CertifiableFieldResource<T> {

    @SneakyThrows
    public DummyCertifiableFieldResource(Class<T> clazz) {
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