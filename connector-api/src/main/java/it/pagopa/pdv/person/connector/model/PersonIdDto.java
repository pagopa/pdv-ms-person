package it.pagopa.pdv.person.connector.model;

import lombok.Data;

@Data
public class PersonIdDto implements PersonIdOperations {

    private String globalId;
    private String namespace;
    private String namespacedId;

}
