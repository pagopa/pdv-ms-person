package it.pagopa.pdv.person.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonGlobalId {

    @JsonProperty(required = true)
    private UUID id;

}
