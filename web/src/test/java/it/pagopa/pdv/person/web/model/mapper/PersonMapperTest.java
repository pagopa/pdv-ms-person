package it.pagopa.pdv.person.web.model.mapper;

import it.pagopa.pdv.person.TestUtils;
import it.pagopa.pdv.person.connector.model.*;
import it.pagopa.pdv.person.web.model.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    @Test
    void assemblesPersonIdDto_nullId() {
        // given
        UUID id = null;
        String namespace = "selfcare";
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        // when
        PersonIdDto model = PersonMapper.assembles(id, namespace, savePersonNamespaceDto);
        // then
        assertNull(model);
    }


    @Test
    void assemblesPersonIdDto_nullSavePersonNamespaceDto() {
        // given
        UUID id = UUID.randomUUID();
        String namespace = "selfcare";
        SavePersonNamespaceDto savePersonNamespaceDto = null;
        // when
        PersonIdDto model = PersonMapper.assembles(id, namespace, savePersonNamespaceDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getGlobalId());
        assertEquals(namespace, model.getNamespace());
        assertNull(model.getNamespacedId());
    }


    @Test
    void assemblesPersonIdDto_nullNamespacedId() {
        // given
        UUID id = UUID.randomUUID();
        String namespace = "selfcare";
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        // when
        PersonIdDto model = PersonMapper.assembles(id, namespace, savePersonNamespaceDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getGlobalId());
        assertEquals(namespace, model.getNamespace());
        assertNull(model.getNamespacedId());
    }


    @Test
    void assemblesPersonIdDto_notNull() {
        // given
        UUID id = UUID.randomUUID();
        String namespace = "selfcare";
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        savePersonNamespaceDto.setNamespacedId(UUID.randomUUID());
        // when
        PersonIdDto model = PersonMapper.assembles(id, namespace, savePersonNamespaceDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getGlobalId());
        assertEquals(namespace, model.getNamespace());
        assertEquals(savePersonNamespaceDto.getNamespacedId().toString(), model.getNamespacedId());
    }


    @Test
    void assemblesPersonDto_nullId() {
        // given
        UUID id = null;
        SavePersonDto savePersonDto = new SavePersonDto();
        // when
        PersonDto model = PersonMapper.assembles(id, savePersonDto);
        // then
        assertNull(model);
    }


    @Test
    void assemblesPersonDto_nullSavePersonDto() {
        // given
        UUID id = UUID.randomUUID();
        SavePersonDto savePersonDto = null;
        // when
        PersonDto model = PersonMapper.assembles(id, savePersonDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getId());
        assertNull(model.getName());
        assertNull(model.getFamilyName());
        assertNull(model.getEmail());
        assertNull(model.getBirthDate());
        assertNull(model.getWorkContacts());
    }


    @Test
    void assemblesPersonDto_nullWorkContacts() {
        // given
        UUID id = UUID.randomUUID();
        SavePersonDto savePersonDto = TestUtils.mockInstance(new SavePersonDto(), "setWorkContacts");
        // when
        PersonDto model = PersonMapper.assembles(id, savePersonDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getId());
        assertEquals(savePersonDto.getName(), model.getName());
        assertEquals(savePersonDto.getFamilyName(), model.getFamilyName());
        assertEquals(savePersonDto.getEmail(), model.getEmail());
        assertEquals(savePersonDto.getBirthDate(), model.getBirthDate());
        assertNull(model.getWorkContacts());
    }

    @Test
    void assemblesPersonDto_notNull() {
        // given
        UUID id = UUID.randomUUID();
        SavePersonDto savePersonDto = TestUtils.mockInstance(new SavePersonDto(), "setWorkContacts");
        savePersonDto.setWorkContacts(Map.of("inst-1", new WorkContactResource(),
                "inst-2", new WorkContactResource()));
        // when
        PersonDto model = PersonMapper.assembles(id, savePersonDto);
        // then
        assertNotNull(model);
        assertEquals(id.toString(), model.getId());
        assertEquals(savePersonDto.getName(), model.getName());
        assertEquals(savePersonDto.getFamilyName(), model.getFamilyName());
        assertEquals(savePersonDto.getEmail(), model.getEmail());
        assertEquals(savePersonDto.getBirthDate(), model.getBirthDate());
        assertNotNull(model.getWorkContacts());
        assertEquals(savePersonDto.getWorkContacts().size(), savePersonDto.getWorkContacts().size());
        assertIterableEquals(savePersonDto.getWorkContacts().keySet(), savePersonDto.getWorkContacts().keySet());
    }


    @Test
    void toWorkContactDto_null() {
        // given
        WorkContactResource workContactResource = null;
        // when
        PersonDto.WorkContactDto model = PersonMapper.toDto(workContactResource);
        // then
        assertNull(model);
    }


    @Test
    void toWorkContactDto_notNull() {
        // given
        CertifiableFieldResource<String> stringCertifiableFieldResource = new CertifiableFieldResource<>();
        stringCertifiableFieldResource.setCertification("certification");
        stringCertifiableFieldResource.setValue("value");
        WorkContactResource workContactResource = new WorkContactResource();
        workContactResource.setEmail(stringCertifiableFieldResource);
        // when
        PersonDto.WorkContactDto model = PersonMapper.toDto(workContactResource);
        // then
        assertNotNull(model);
        assertEquals(workContactResource.getEmail(), model.getEmail());
    }


    @Test
    void toPersonResource_null() {
        // given
        PersonDetailsOperations personDetailsOperations = null;
        // when
        PersonResource model = PersonMapper.toResource(personDetailsOperations);
        // then
        assertNull(model);
    }


    @Test
    void toPersonResource_notNull() {
        // given
        PersonDetailsOperations personDetailsOperations = new DummyPersonDetails();
        // when
        PersonResource model = PersonMapper.toResource(personDetailsOperations);
        // then
        assertNotNull(model);
        assertEquals(personDetailsOperations.getId(), model.getId().toString());
        assertCertifiableFieldEquals(personDetailsOperations.getName(), model.getName());
        assertCertifiableFieldEquals(personDetailsOperations.getFamilyName(), model.getFamilyName());
        assertCertifiableFieldEquals(personDetailsOperations.getEmail(), model.getEmail());
        assertCertifiableFieldEquals(personDetailsOperations.getBirthDate(), model.getBirthDate());
        assertNotNull(personDetailsOperations.getWorkContacts());
        assertFalse(personDetailsOperations.getWorkContacts().isEmpty());
        assertIterableEquals(personDetailsOperations.getWorkContacts().keySet(), model.getWorkContacts().keySet());
    }

    private void assertCertifiableFieldEquals(CertifiableField<?> expected, CertifiableField<?> actual) {
        assertEquals(expected.getCertification(), actual.getCertification());
        assertEquals(expected.getValue(), actual.getValue());
    }


    @Test
    void toWorkContactResource_null() {
        // given
        PersonDetailsOperations.WorkContactOperations workContactOperations = null;
        // when
        WorkContactResource model = PersonMapper.toResource(workContactOperations);
        // then
        assertNull(model);
    }

    @Test
    void toWorkContactResource_notNull() {
        // given
        PersonDetailsOperations.WorkContactOperations workContactOperations = new DummyPersonDetails.DummyWorkContact();
        // when
        WorkContactResource model = PersonMapper.toResource(workContactOperations);
        // then
        assertNotNull(model);
        assertCertifiableFieldEquals(workContactOperations.getEmail(), model.getEmail());
    }

    @Test
    void toWorkContactResource_nullValue() {
        // given
        PersonDetailsOperations.WorkContactOperations workContactOperations = new DummyPersonDetails.DummyWorkContactNullValue();
        // when
        WorkContactResource model = PersonMapper.toResource(workContactOperations);
        // then
        assertNotNull(model);
        assertCertifiableFieldEquals(workContactOperations.getEmail(), model.getEmail());
    }
}