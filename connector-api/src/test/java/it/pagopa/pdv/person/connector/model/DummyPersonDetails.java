package it.pagopa.pdv.person.connector.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

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
        this.name = new DummyCertifiedField<>(String.class);
        this.familyName = new DummyCertifiedField<>(String.class);
        this.email = new DummyCertifiedField<>(String.class);
        this.birthDate = new DummyCertifiedField<>(LocalDate.class);
        this.workContacts = Map.of("inst-1", new DummyWorkContact());
    }

    @Data
    public static class DummyWorkContact implements WorkContactOperations {
        private CertifiableField<String> email;
        private CertifiableField<String> mobilePhone;
        private CertifiableField<String> telephone;

        public DummyWorkContact() {

            this.email = new DummyCertifiedField<>(String.class);
            this.mobilePhone = new DummyCertifiedField<>(String.class);
            this.telephone = new DummyCertifiedField<>(String.class);
        }
    }
    @Data
    public static class DummyWorkContactNullValue implements WorkContactOperations {
        private CertifiableField<String> email;
        private CertifiableField<String> mobilePhone;
        private CertifiableField<String> telephone;

        public DummyWorkContactNullValue() {

            this.email = new DummyCertifiedField<>();
            this.mobilePhone = new DummyCertifiedField<>(String.class);
            this.telephone = new DummyCertifiedField<>(String.class);        }
    }

}
