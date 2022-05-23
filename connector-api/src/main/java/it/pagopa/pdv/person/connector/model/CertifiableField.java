package it.pagopa.pdv.person.connector.model;

public interface CertifiableField<T> {

    String NOT_CERTIFIED_VALUE = "NONE";

    String getCertification();

    void setCertification(String certification);

    T getValue();

    void setValue(T value);

}
