package it.pagopa.pdv.person.connector.model;

import java.time.LocalDate;
import java.util.Map;
import lombok.Data;

@Data
public class PersonDto implements PersonDetailsOperations {

    private String id;
    private String fiscalCode;
    private CertifiableField<String> name;
    private CertifiableField<String> familyName;
    private CertifiableField<String> email;
    private CertifiableField<LocalDate> birthDate;
    private Map<String, WorkContactDto> workContacts;


    @Data
    public static class WorkContactDto implements WorkContactOperations {
        private CertifiableField<String> email;
        private CertifiableField<String> mobilePhone;
        private CertifiableField<String> telephone;
    }

}
