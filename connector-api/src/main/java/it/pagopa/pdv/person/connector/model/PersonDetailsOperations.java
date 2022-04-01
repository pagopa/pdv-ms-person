package it.pagopa.pdv.person.connector.model;


import java.util.Map;

public interface PersonDetailsOperations {

    String getId();

    String getGivenName();

    String getFamilyName();

    Map<String, ? extends WorkContactOperations> getWorkContacts();


    interface WorkContactOperations {

        String getEmail();

    }

}
