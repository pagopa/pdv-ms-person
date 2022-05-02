package it.pagopa.pdv.person.web.controller;

import com.jayway.jsonpath.JsonPath;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import it.pagopa.pdv.person.connector.model.DummyPersonDetails;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.config.WebTestConfig;
import it.pagopa.pdv.person.web.handler.RestExceptionsHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/{id}", uuid)
                .queryParam("isNamespaced", isNamespaced.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name.certification", notNullValue()))
                .andExpect(jsonPath("$.name.value", notNullValue()))
                .andExpect(jsonPath("$.familyName.certification", notNullValue()))
                .andExpect(jsonPath("$.familyName.value", notNullValue()))
                .andExpect(jsonPath("$.email.certification", notNullValue()))
                .andExpect(jsonPath("$.email.value", notNullValue()))
                .andExpect(jsonPath("$.birthDate.certification", notNullValue()))
                .andExpect(jsonPath("$.birthDate.value", notNullValue()))
                .andExpect(jsonPath("$.birthDate.value", notNullValue()))
                .andExpect(jsonPath("$.workContacts..email.certification", notNullValue()))
                .andExpect(jsonPath("$.workContacts..email.value", notNullValue()));
        // then
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
        mvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/id")
                .queryParam("namespacedId", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", not(uuid)));
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .findIdByNamespacedId(uuid.toString());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void saveNamespacedId(@Value("classpath:stubs/savePersonNamespaceDto.json") Resource savePersonNamespaceDto) throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        String namespace = "selfcare";
        Mockito.doNothing().when(personServiceMock)
                .save(Mockito.any(PersonIdOperations.class));
        // when
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/{id}/namespace/{namespace}", uuid, namespace)
                .content(savePersonNamespaceDto.getInputStream().readAllBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .save(personIdOperationsCaptor.capture());
        PersonIdOperations personIdOperations = personIdOperationsCaptor.getValue();
        assertNotNull(personIdOperations);
        assertEquals(uuid.toString(), personIdOperations.getGlobalId());
        assertEquals(namespace, personIdOperations.getNamespace());
        String stubbedNamespacedId = JsonPath.parse(savePersonNamespaceDto.getInputStream()).read("$.namespacedId", String.class);
        assertEquals(stubbedNamespacedId, personIdOperations.getNamespacedId());
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    @Test
    void save(@Value("classpath:stubs/savePersonDto.json") Resource savePersonDto) throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.doNothing().when(personServiceMock)
                .save(Mockito.any(PersonDetailsOperations.class));
        // when
        mvc.perform(MockMvcRequestBuilders
                .patch(BASE_URL + "/{id}", uuid)
                .content(savePersonDto.getInputStream().readAllBytes())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // then
        Mockito.verify(personServiceMock, Mockito.times(1))
                .save(personDetailsOperationsCaptor.capture());
        PersonDetailsOperations personDetailsOperations = personDetailsOperationsCaptor.getValue();
        assertNotNull(personDetailsOperations);
        assertEquals(uuid.toString(), personDetailsOperations.getId());
        Map<String, Object> requestContent = JsonPath.parse(savePersonDto.getInputStream()).json();
        assertCertifiableFieldEquals(requestContent.get("name"), personDetailsOperations.getName());
        assertCertifiableFieldEquals(requestContent.get("familyName"), personDetailsOperations.getFamilyName());
        assertCertifiableFieldEquals(requestContent.get("email"), personDetailsOperations.getEmail());
        assertCertifiableFieldEquals(requestContent.get("birthDate"), personDetailsOperations.getBirthDate());
        Map<String, Map<String, Object>> workContacts =
                (Map<String, Map<String, Object>>) requestContent.get("workContacts");
        assertEquals(workContacts.size(), personDetailsOperations.getWorkContacts().size());
        personDetailsOperations.getWorkContacts().forEach((s, workContact) ->
                assertCertifiableFieldEquals(workContacts.get(s).get("email"), workContact.getEmail()));
        Mockito.verifyNoMoreInteractions(personServiceMock);
    }


    private void assertCertifiableFieldEquals(Object expected, CertifiableField<?> actual) {
        Map<String, Object> jsonMap = (Map<String, Object>) expected;
        assertEquals(jsonMap.get("certification"), actual.getCertification());
        assertEquals(jsonMap.get("value").toString(), actual.getValue().toString());
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