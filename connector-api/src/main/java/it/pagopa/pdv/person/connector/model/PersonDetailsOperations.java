package it.pagopa.pdv.person.connector.model;


import java.time.LocalDate;
import java.util.Map;

public interface PersonDetailsOperations {

    String getId();

    CertifiableField<String> getName();

    CertifiableField<String> getFamilyName();

    CertifiableField<String> getEmail();

    CertifiableField<LocalDate> getBirthDate();

    Map<String, ? extends WorkContactOperations> getWorkContacts();


    interface WorkContactOperations {

        CertifiableField<String> getEmail();

    }

}
