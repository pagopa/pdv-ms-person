package it.pagopa.pdv.person.web.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonResource extends PersonResource {

    public DummyPersonResource() {
        setId(UUID.randomUUID());
        setName(new DummyCertifiableFieldResource<>(String.class));
        setFamilyName(new DummyCertifiableFieldResource<>(String.class));
        setEmail(new DummyCertifiableFieldResource<>(String.class));
        setBirthDate(new DummyCertifiableFieldResource<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContactResource()));
    }


    public static class DummyWorkContactResource extends WorkContactResource {
        public DummyWorkContactResource() {
            setEmail(new DummyCertifiableFieldResource<>(String.class));
        }
    }

}
