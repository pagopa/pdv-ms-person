package it.pagopa.pdv.person.web.model;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class PersonIdResource {

    private UUID globalId;
    private String namespace;
    private UUID namespacedId;

}
