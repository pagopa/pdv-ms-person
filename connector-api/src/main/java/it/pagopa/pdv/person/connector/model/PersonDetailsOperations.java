package it.pagopa.pdv.person.connector.model;


import java.time.LocalDate;
import java.util.Map;

public interface PersonDetailsOperations {

    String getId();

    CertifiableField<String> getName();

    CertifiableField<String> getFamilyName();

    CertifiableField<String> getEmail();

    CertifiableField<LocalDate> getBirthDate();

    CertifiableField<String> getSpidCode();

    CertifiableField<String> getPlaceOfBirth();

    CertifiableField<String> getCountyOfBirth();

    CertifiableField<String> getGender();

    CertifiableField<String> getCompanyName();

    CertifiableField<String> getRegisteredOffice();

    CertifiableField<String> getIvaCode();

    CertifiableField<String> getIdCard();

    CertifiableField<String> getMobilePhone();

    CertifiableField<String> getAddress();

    CertifiableField<LocalDate> getExpirationDate();

    CertifiableField<String> getDigitalAddress();

    CertifiableField<String> getDomicileAddress();

    CertifiableField<String> getDomicilePlace();

    CertifiableField<String> getDomicilePostalCode();

    CertifiableField<String> getDomicileProvince();

    CertifiableField<String> getDomicileCountry();

    CertifiableField<String> getQualification();

    CertifiableField<String> getCommonName();

    CertifiableField<String> getSurname();

    CertifiableField<String> getGivenName();

    CertifiableField<String> getPreferredUsername();

    CertifiableField<String> getTitle();

    CertifiableField<String> getUserCertificate();

    CertifiableField<String> getEmployeeNumber();

    CertifiableField<String> getOrgUnitName();

    CertifiableField<String> getPreferredLanguage();

    CertifiableField<String> getCountry();

    CertifiableField<String> getStateOrProvince();

    CertifiableField<String> getCity();

    CertifiableField<String> getPostalCode();

    CertifiableField<String> getStreet();

    Map<String, ? extends WorkContactOperations> getWorkContacts();


    interface WorkContactOperations {

        CertifiableField<String> getEmail();

        CertifiableField<String> getMobilePhone();
        
        CertifiableField<String> getTelephone();

    }

}
