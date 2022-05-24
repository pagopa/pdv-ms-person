package it.pagopa.pdv.person.web.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonResource extends PersonResource {

    public DummyPersonResource() {
        setId(UUID.randomUUID());
        setName(new DummyCertifiedFieldResource<>(String.class));
        setFamilyName(new DummyCertifiedFieldResource<>(String.class));
        setEmail(new DummyCertifiedFieldResource<>(String.class));
        setBirthDate(new DummyCertifiedFieldResource<>(LocalDate.class));
        setWorkContacts(Map.of("inst-1", new DummyWorkContactResource()));
    }


    public static class DummyWorkContactResource extends WorkContactResource {
        public DummyWorkContactResource() {
            setEmail(new DummyCertifiedFieldResource<>(String.class));
        }
    }

}
