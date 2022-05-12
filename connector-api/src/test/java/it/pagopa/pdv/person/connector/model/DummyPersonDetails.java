package it.pagopa.pdv.person.connector.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
public class DummyPersonDetails implements PersonDetailsOperations {

    private String id;
    private String fiscalCode;
    private CertifiableField<String> name;
    private CertifiableField<String> familyName;
    private CertifiableField<String> email;
    private CertifiableField<LocalDate> birthDate;
    private Map<String, WorkContactOperations> workContacts;


    public DummyPersonDetails() {
        this.id = UUID.randomUUID().toString();
        this.fiscalCode = "fiscalCode";
        this.name = new DummyCertifiableField<>(String.class);
        this.familyName = new DummyCertifiableField<>(String.class);
        this.email = new DummyCertifiableField<>(String.class);
        this.birthDate = new DummyCertifiableField<>(LocalDate.class);
        this.workContacts = Map.of("inst-1", new DummyWorkContact());
    }


    @Data
    public static class DummyWorkContact implements WorkContactOperations {
        private CertifiableField<String> email;

        public DummyWorkContact() {
            this.email = new DummyCertifiableField<>(String.class);
        }
    }

}
