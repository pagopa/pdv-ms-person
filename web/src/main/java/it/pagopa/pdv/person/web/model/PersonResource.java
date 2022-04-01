package it.pagopa.pdv.person.web.model;

import lombok.Data;

import java.util.Map;

@Data
public class PersonResource {

    private String id;
    private String givenName;
    private String familyName;
    private Map<String, WorkContactResource> workContacts;


    @Data
    public static class WorkContactResource {
        private String email;
    }

}
