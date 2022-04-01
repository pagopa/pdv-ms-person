package it.pagopa.pdv.person.web.model;

import lombok.Data;

import java.util.Map;

@Data
public class SavePersonDto {

    private String givenName;
    private String familyName;
    private Map<String, PersonResource.WorkContactResource> workContacts;


    @Data
    public static class WorkContact {
        private String email;
    }

}
