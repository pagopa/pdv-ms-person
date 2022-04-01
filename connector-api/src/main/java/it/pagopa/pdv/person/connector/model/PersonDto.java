package it.pagopa.pdv.person.connector.model;

import lombok.Data;

import java.util.Map;

@Data
public class PersonDto implements PersonDetailsOperations {

    private String id;
    private String fiscalCode;
    private String givenName;
    private String familyName;
    private Map<String, WorkContactDto> workContacts;


    @Data
    public static class WorkContactDto implements WorkContactOperations {
        private String email;
    }

}
