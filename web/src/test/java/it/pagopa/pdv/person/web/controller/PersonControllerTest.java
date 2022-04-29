package it.pagopa.pdv.person.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.pdv.person.connector.model.DummyPersonDetails;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.config.WebTestConfig;
import it.pagopa.pdv.person.web.handler.RestExceptionsHandler;
import it.pagopa.pdv.person.web.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = {PersonController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        PersonController.class,
        RestExceptionsHandler.class,
        WebTestConfig.class
})
class PersonControllerTest {

    private static final String BASE_URL = "/people";

    @MockBean
    private PersonService personServiceMock;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<PersonIdOperations> personIdOperationsCaptor;

    @Captor
    private ArgumentCaptor<PersonDetailsOperations> personDetailsOperationsCaptor;


    @Test
    void findById() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Boolean isNamespaced = Boolean.FALSE;
        Mockito.when(personServiceMock.findById(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(new DummyPersonDetails());
        // when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/{id}", uuid)
                .queryParam("isNamespaced", isNamespaced.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // then
        PersonResource person = objectMapper.readValue(result.getResponse().getContentAsString(), PersonResource.class);
        assertNotNull(person);
        Mockito.verify(personServiceMock, Mockito.times(1))
                .findById(uuid.toString(), isNamespaced);
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void findIdByNamespacedId() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.when(personServiceMock.findIdByNamespacedId(Mockito.anyString()))
                .thenReturn(UUID.randomUUID().toString());
        // when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/id")
                .queryParam("namespacedId", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // then
        PersonId personId = objectMapper.readValue(result.getResponse().getContentAsString(), PersonId.class);
        assertNotNull(personId);
        assertNotEquals(uuid, personId.getId());
        Mockito.verify(personServiceMock, Mockito.times(1))
                .findIdByNamespacedId(uuid.toString());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void saveNamespacedId() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        String namespace = "selfcare";
        SavePersonNamespaceDto savePersonNamespaceDto = new SavePersonNamespaceDto();
        savePersonNamespaceDto.setNamespacedId(UUID.randomUUID());
        Mockito.doNothing().when(personServiceMock)
                .save(Mockito.any(PersonIdOperations.class));
        // when
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/{id}/namespace/{namespace}", uuid, namespace)
                .content(objectMapper.writeValueAsString(savePersonNamespaceDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .save(personIdOperationsCaptor.capture());
        PersonIdOperations personIdOperations = personIdOperationsCaptor.getValue();
        assertNotNull(personIdOperations);
        assertEquals(uuid.toString(), personIdOperations.getGlobalId());
        assertEquals(namespace, personIdOperations.getNamespace());
        assertEquals(savePersonNamespaceDto.getNamespacedId().toString(), personIdOperations.getNamespacedId());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void save() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        SavePersonDto savePersonDto = new SavePersonDto();
        CertifiableFieldResource<String> stringCertifiableFieldResource = new CertifiableFieldResource<>();
        stringCertifiableFieldResource.setCertification("certification");
        stringCertifiableFieldResource.setValue("value");
        CertifiableFieldResource<LocalDate> localDateCertifiableFieldResource = new CertifiableFieldResource<>();
        localDateCertifiableFieldResource.setCertification("certification");
        localDateCertifiableFieldResource.setValue(LocalDate.now());
        savePersonDto.setName(stringCertifiableFieldResource);
        savePersonDto.setFamilyName(stringCertifiableFieldResource);
        savePersonDto.setEmail(stringCertifiableFieldResource);
        savePersonDto.setBirthDate(localDateCertifiableFieldResource);
        WorkContactResource workContactResource = new WorkContactResource();
        workContactResource.setEmail(stringCertifiableFieldResource);
        savePersonDto.setWorkContacts(Map.of("inst-1", workContactResource));
        Mockito.doNothing().when(personServiceMock)
                .save(Mockito.any(PersonDetailsOperations.class));
        // when
        mvc.perform(MockMvcRequestBuilders
                .patch(BASE_URL + "/{id}", uuid)
                .content(objectMapper.writeValueAsString(savePersonDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .save(personDetailsOperationsCaptor.capture());
        PersonDetailsOperations personDetailsOperations = personDetailsOperationsCaptor.getValue();
        assertNotNull(personDetailsOperations);
        assertEquals(uuid.toString(), personDetailsOperations.getId());
        assertEquals(savePersonDto.getName(), personDetailsOperations.getName());
        assertEquals(savePersonDto.getFamilyName(), personDetailsOperations.getFamilyName());
        assertEquals(savePersonDto.getEmail(), personDetailsOperations.getEmail());
        assertEquals(savePersonDto.getBirthDate(), personDetailsOperations.getBirthDate());
        assertEquals(savePersonDto.getWorkContacts().size(), personDetailsOperations.getWorkContacts().size());
        assertEquals(savePersonDto.getWorkContacts().get("inst-1").getEmail(), personDetailsOperations.getWorkContacts().get("inst-1").getEmail());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void deletePerson() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.doNothing().when(personServiceMock)
                .deleteById(Mockito.any());
        // when
        mvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + "/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .deleteById(uuid.toString());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void deletePersonNamespace() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        String namespace = "selfcare";
        Mockito.doNothing().when(personServiceMock)
                .deleteById(Mockito.any(), Mockito.any());
        // when
        mvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + "/{id}/namespace/{namespace}", uuid, namespace)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .deleteById(uuid.toString(), namespace);
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }

}