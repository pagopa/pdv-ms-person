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
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = {
    PersonController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
    PersonController.class,
    RestExceptionsHandler.class,
    WebTestConfig.class
})
class PersonControllerTest {

  private static final String BASE_URL = "/people";

  private static final String NAMESPACE_REQUEST_PARAM = "namespace";

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
    Optional<String> namespace = Optional.of("namespace");
    Mockito.when(
            personServiceMock.findById(Mockito.anyString(), Mockito.anyBoolean(), Mockito.any()))
        .thenReturn(new DummyPersonDetails());
    // when
    mvc.perform(MockMvcRequestBuilders
            .get(BASE_URL + "/{id}", uuid)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam(NAMESPACE_REQUEST_PARAM, namespace.get())
            .queryParam("isNamespaced", "true")
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
        .andExpect(jsonPath("$.spidCode.certification", notNullValue()))
        .andExpect(jsonPath("$.spidCode.value", notNullValue()))
        .andExpect(jsonPath("$.placeOfBirth.certification", notNullValue()))
        .andExpect(jsonPath("$.placeOfBirth.value", notNullValue()))
        .andExpect(jsonPath("$.countyOfBirth.certification", notNullValue()))
        .andExpect(jsonPath("$.countyOfBirth.value", notNullValue()))
        .andExpect(jsonPath("$.gender.certification", notNullValue()))
        .andExpect(jsonPath("$.gender.value", notNullValue()))
        .andExpect(jsonPath("$.companyName.certification", notNullValue()))
        .andExpect(jsonPath("$.companyName.value", notNullValue()))
        .andExpect(jsonPath("$.registeredOffice.certification", notNullValue()))
        .andExpect(jsonPath("$.registeredOffice.value", notNullValue()))
        .andExpect(jsonPath("$.ivaCode.certification", notNullValue()))
        .andExpect(jsonPath("$.ivaCode.value", notNullValue()))
        .andExpect(jsonPath("$.idCard.certification", notNullValue()))
        .andExpect(jsonPath("$.idCard.value", notNullValue()))
        .andExpect(jsonPath("$.mobilePhone.certification", notNullValue()))
        .andExpect(jsonPath("$.mobilePhone.value", notNullValue()))
        .andExpect(jsonPath("$.address.certification", notNullValue()))
        .andExpect(jsonPath("$.address.value", notNullValue()))
        .andExpect(jsonPath("$.expirationDate.certification", notNullValue()))
        .andExpect(jsonPath("$.expirationDate.value", notNullValue()))
        .andExpect(jsonPath("$.digitalAddress.certification", notNullValue()))
        .andExpect(jsonPath("$.digitalAddress.value", notNullValue()))
        .andExpect(jsonPath("$.domicileAddress.certification", notNullValue()))
        .andExpect(jsonPath("$.domicileAddress.value", notNullValue()))
        .andExpect(jsonPath("$.domicilePlace.certification", notNullValue()))
        .andExpect(jsonPath("$.domicilePlace.value", notNullValue()))
        .andExpect(jsonPath("$.domicilePostalCode.certification", notNullValue()))
        .andExpect(jsonPath("$.domicilePostalCode.value", notNullValue()))
        .andExpect(jsonPath("$.domicileProvince.certification", notNullValue()))
        .andExpect(jsonPath("$.domicileProvince.value", notNullValue()))
        .andExpect(jsonPath("$.domicileCountry.certification", notNullValue()))
        .andExpect(jsonPath("$.domicileCountry.value", notNullValue()))
        .andExpect(jsonPath("$.qualification.certification", notNullValue()))
        .andExpect(jsonPath("$.qualification.value", notNullValue()))
        .andExpect(jsonPath("$.commonName.certification", notNullValue()))
        .andExpect(jsonPath("$.commonName.value", notNullValue()))
        .andExpect(jsonPath("$.surname.certification", notNullValue()))
        .andExpect(jsonPath("$.surname.value", notNullValue()))
        .andExpect(jsonPath("$.givenName.certification", notNullValue()))
        .andExpect(jsonPath("$.givenName.value", notNullValue()))
        .andExpect(jsonPath("$.preferredUsername.certification", notNullValue()))
        .andExpect(jsonPath("$.preferredUsername.value", notNullValue()))
        .andExpect(jsonPath("$.title.certification", notNullValue()))
        .andExpect(jsonPath("$.title.value", notNullValue()))
        .andExpect(jsonPath("$.userCertificate.certification", notNullValue()))
        .andExpect(jsonPath("$.userCertificate.value", notNullValue()))
        .andExpect(jsonPath("$.employeeNumber.certification", notNullValue()))
        .andExpect(jsonPath("$.employeeNumber.value", notNullValue()))
        .andExpect(jsonPath("$.orgUnitName.certification", notNullValue()))
        .andExpect(jsonPath("$.orgUnitName.value", notNullValue()))
        .andExpect(jsonPath("$.preferredLanguage.certification", notNullValue()))
        .andExpect(jsonPath("$.preferredLanguage.value", notNullValue()))
        .andExpect(jsonPath("$.country.certification", notNullValue()))
        .andExpect(jsonPath("$.country.value", notNullValue()))
        .andExpect(jsonPath("$.stateOrProvince.certification", notNullValue()))
        .andExpect(jsonPath("$.stateOrProvince.value", notNullValue()))
        .andExpect(jsonPath("$.city.certification", notNullValue()))
        .andExpect(jsonPath("$.city.value", notNullValue()))
        .andExpect(jsonPath("$.postalCode.certification", notNullValue()))
        .andExpect(jsonPath("$.postalCode.value", notNullValue()))
        .andExpect(jsonPath("$.street.certification", notNullValue()))
        .andExpect(jsonPath("$.street.value", notNullValue()))
        .andExpect(jsonPath("$.workContacts..email.certification", notNullValue()))
        .andExpect(jsonPath("$.workContacts..email.value", notNullValue()))
        .andExpect(jsonPath("$.workContacts..mobilePhone.certification", notNullValue()))
        .andExpect(jsonPath("$.workContacts..mobilePhone.value", notNullValue()))
        .andExpect(jsonPath("$.workContacts..telephone.certification", notNullValue()))
        .andExpect(jsonPath("$.workContacts..telephone.value", notNullValue()));
    // then
    Mockito.verify(personServiceMock, Mockito.times(1))
        .findById(uuid.toString(), true, namespace);
    Mockito.verifyNoMoreInteractions(personServiceMock);
  }


  @Test
  void findIdByNamespacedId() throws Exception {
    // given
    UUID uuid = UUID.randomUUID();
    String namespace = "namespace";
    Mockito.when(personServiceMock.findIdByNamespacedId(Mockito.anyString(), Mockito.any()))
        .thenReturn(UUID.randomUUID().toString());
    // when
    mvc.perform(MockMvcRequestBuilders
            .get(BASE_URL + "/id")
            .queryParam("namespacedId", uuid.toString())
            .queryParam(NAMESPACE_REQUEST_PARAM, namespace)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.id", not(uuid)));
    // then
    Mockito.verify(personServiceMock, Mockito.times(1))
        .findIdByNamespacedId(uuid.toString(), namespace);
    Mockito.verifyNoMoreInteractions(personServiceMock);
  }


  @Test
  void saveNamespacedId(
      @Value("classpath:stubs/savePersonNamespaceDto.json") Resource savePersonNamespaceDto)
      throws Exception {
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
    String stubbedNamespacedId = JsonPath.parse(savePersonNamespaceDto.getInputStream())
        .read("$.namespacedId", String.class);
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
    assertCertifiableFieldEquals(requestContent.get("familyName"),
        personDetailsOperations.getFamilyName());
    assertCertifiableFieldEquals(requestContent.get("email"), personDetailsOperations.getEmail());
    assertCertifiableFieldEquals(requestContent.get("birthDate"),
        personDetailsOperations.getBirthDate());
    assertCertifiableFieldEquals(requestContent.get("spidCode"),
        personDetailsOperations.getSpidCode());
    assertCertifiableFieldEquals(requestContent.get("placeOfBirth"),
        personDetailsOperations.getPlaceOfBirth());
    assertCertifiableFieldEquals(requestContent.get("countyOfBirth"),
        personDetailsOperations.getCountyOfBirth());
    assertCertifiableFieldEquals(requestContent.get("gender"), personDetailsOperations.getGender());
    assertCertifiableFieldEquals(requestContent.get("companyName"),
        personDetailsOperations.getCompanyName());
    assertCertifiableFieldEquals(requestContent.get("registeredOffice"),
        personDetailsOperations.getRegisteredOffice());
    assertCertifiableFieldEquals(requestContent.get("ivaCode"),
        personDetailsOperations.getIvaCode());
    assertCertifiableFieldEquals(requestContent.get("idCard"), personDetailsOperations.getIdCard());
    assertCertifiableFieldEquals(requestContent.get("mobilePhone"),
        personDetailsOperations.getMobilePhone());
    assertCertifiableFieldEquals(requestContent.get("address"),
        personDetailsOperations.getAddress());
    assertCertifiableFieldEquals(requestContent.get("expirationDate"),
        personDetailsOperations.getExpirationDate());
    assertCertifiableFieldEquals(requestContent.get("digitalAddress"),
        personDetailsOperations.getDigitalAddress());
    assertCertifiableFieldEquals(requestContent.get("domicileAddress"),
        personDetailsOperations.getDomicileAddress());
    assertCertifiableFieldEquals(requestContent.get("domicilePlace"),
        personDetailsOperations.getDomicilePlace());
    assertCertifiableFieldEquals(requestContent.get("domicilePostalCode"),
        personDetailsOperations.getDomicilePostalCode());
    assertCertifiableFieldEquals(requestContent.get("domicileProvince"),
        personDetailsOperations.getDomicileProvince());
    assertCertifiableFieldEquals(requestContent.get("domicileCountry"),
        personDetailsOperations.getDomicileCountry());
    assertCertifiableFieldEquals(requestContent.get("qualification"),
        personDetailsOperations.getQualification());
    assertCertifiableFieldEquals(requestContent.get("commonName"),
        personDetailsOperations.getCommonName());
    assertCertifiableFieldEquals(requestContent.get("surname"),
        personDetailsOperations.getSurname());
    assertCertifiableFieldEquals(requestContent.get("givenName"),
        personDetailsOperations.getGivenName());
    assertCertifiableFieldEquals(requestContent.get("preferredUsername"),
        personDetailsOperations.getPreferredUsername());
    assertCertifiableFieldEquals(requestContent.get("title"), personDetailsOperations.getTitle());
    assertCertifiableFieldEquals(requestContent.get("userCertificate"),
        personDetailsOperations.getUserCertificate());
    assertCertifiableFieldEquals(requestContent.get("employeeNumber"),
        personDetailsOperations.getEmployeeNumber());
    assertCertifiableFieldEquals(requestContent.get("orgUnitName"),
        personDetailsOperations.getOrgUnitName());
    assertCertifiableFieldEquals(requestContent.get("preferredLanguage"),
        personDetailsOperations.getPreferredLanguage());
    assertCertifiableFieldEquals(requestContent.get("country"),
        personDetailsOperations.getCountry());
    assertCertifiableFieldEquals(requestContent.get("stateOrProvince"),
        personDetailsOperations.getStateOrProvince());
    assertCertifiableFieldEquals(requestContent.get("city"), personDetailsOperations.getCity());
    assertCertifiableFieldEquals(requestContent.get("postalCode"),
        personDetailsOperations.getPostalCode());
    assertCertifiableFieldEquals(requestContent.get("street"), personDetailsOperations.getStreet());

// Assertions for the nested workContacts map
    Map<String, Map<String, Object>> workContacts =
        (Map<String, Map<String, Object>>) requestContent.get("workContacts");
    assertEquals(workContacts.size(), personDetailsOperations.getWorkContacts().size());
    personDetailsOperations.getWorkContacts().forEach((key, workContact) -> {
      Map<String, Object> requestWorkContact = workContacts.get(key);
      assertCertifiableFieldEquals(requestWorkContact.get("email"), workContact.getEmail());
      assertCertifiableFieldEquals(requestWorkContact.get("mobilePhone"),
          workContact.getMobilePhone());
      assertCertifiableFieldEquals(requestWorkContact.get("telephone"), workContact.getTelephone());
    });
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