package it.pagopa.pdv.person.connector.model;

public interface PersonIdOperations {

    String GLOBAL_NAMESPACE = "GLOBAL";

    String getGlobalId();

    String getNamespace();

    String getNamespacedId();

}
