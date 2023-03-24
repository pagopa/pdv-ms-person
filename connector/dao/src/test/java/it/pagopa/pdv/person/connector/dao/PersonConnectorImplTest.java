package it.pagopa.pdv.person.connector.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import it.pagopa.pdv.person.connector.dao.config.DynamoDBTestConfig;
import it.pagopa.pdv.person.connector.dao.model.DummyPersonDetails;
import it.pagopa.pdv.person.connector.dao.model.DummyPersonId;
import it.pagopa.pdv.person.connector.dao.model.PersonDetails;
import it.pagopa.pdv.person.connector.exception.ResourceNotFoundException;
import it.pagopa.pdv.person.connector.exception.UpdateNotAllowedException;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev-local")
@ExtendWith(LocalDBCreationExtension.class)
@SpringBootTest(classes = {DynamoDBTestConfig.class, PersonConnectorImpl.class})
class PersonConnectorImplTest {

    private static final String PERSON_ID_REQUIRED_MESSAGE = "A person id is required";

    @Autowired
    private PersonConnectorImpl personConnector;

    @SpyBean
    private AmazonDynamoDB amazonDynamoDB;

    @SpyBean
    private DynamoDBMapper dynamoDBMapper;
    @BeforeEach
    void init() {
        DynamoDBTestConfig.dynamoDBLocalSetup(amazonDynamoDB, dynamoDBMapper);
    }


    @Test
    void findById_nullId() {
        // given
        String id = null;
        // when
        Executable executable = () -> personConnector.findById(id);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals(PERSON_ID_REQUIRED_MESSAGE, e.getMessage());
    }


    @Test
    void findById_notFound() {
        // given
        String id = "idNotFound";
        // when
        Optional<PersonDetailsOperations> found = personConnector.findById(id);
        // then
        assertTrue(found.isEmpty());
    }


    @Test
    void findById() {
        // given
        final DummyPersonDetails sevedPerson = new DummyPersonDetails();
        personConnector.save(sevedPerson);
        // when
        Optional<PersonDetailsOperations> found = personConnector.findById(sevedPerson.getId());
        // then
        assertTrue(found.isPresent());
        assertEquals(sevedPerson.getId(), found.get().getId());
        assertEquals(sevedPerson, found.get());
    }


    @Test
    void findIdByNamespacedId_nullId() {
        // given
        String namespacedId = null;
        String namespace = "namespace";
        // when
        Executable executable = () -> personConnector.findIdByNamespacedId(namespacedId, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person namespaced id is required", e.getMessage());
    }


    @Test
    void findIdByNamespacedId_notFound() {
        // given
        String namespacedId = "namespacedIdNotFound";
        String namespace = "namespace";
        // when
        final Optional<String> found = personConnector.findIdByNamespacedId(namespacedId, namespace);
        // then
        assertTrue(found.isEmpty());
    }


    @Test
    void findIdByNamespacedId() {
        // given
        final DummyPersonId savedPersonId = new DummyPersonId();
        personConnector.save(savedPersonId);
        // when
        final Optional<String> found = personConnector.findIdByNamespacedId(savedPersonId.getNamespacedId(), savedPersonId.getNamespace());
        // then
        assertTrue(found.isPresent());
        assertEquals(savedPersonId.getGlobalId(), found.get());
    }


    @Test
    void savePersonId_nullId() {
        // given
        PersonIdOperations personId = null;
        // when
        Executable executable = () -> personConnector.save(personId);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals(PERSON_ID_REQUIRED_MESSAGE, e.getMessage());
    }


    @Test
    void savePersonId_newItem() {
        // given
        PersonIdOperations personId = new DummyPersonId();
        // when
        Executable executable = () -> personConnector.save(personId);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonId_alreadyExists() {
        // given
        PersonIdOperations personId = new DummyPersonId();
        personConnector.save(personId);
        // when
        Executable executable = () -> personConnector.save(personId);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonDetails_nullPerson() {
        // given
        PersonDetailsOperations personDetails = null;
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person details is required", e.getMessage());
    }


    @Test
    void savePersonDetails_onlyPrimaryKeySet_newItem() {
        // given
        PersonDetails personDetails = new PersonDetails();
        personDetails.setId(UUID.randomUUID().toString());
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonDetails_onlyPrimaryKeySet_alreadyExists() {
        // given
        PersonDetails personDetails = new PersonDetails();
        personDetails.setId(UUID.randomUUID().toString());
        personConnector.save(personDetails);
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonDetails_newItem() {
        // given
        PersonDetails personDetails = new DummyPersonDetails();
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonDetails_alreadyExists() {
        // given
        PersonDetails personDetails = new DummyPersonDetails();
        personConnector.save(personDetails);
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertDoesNotThrow(executable);
    }


    @Test
    void savePersonDetails_notAllowed_certifiedField() {
        // given
        PersonDetails personDetails = new DummyPersonDetails();
        personDetails.getName().setCertification("SPID");
        personConnector.save(personDetails);
        personDetails.getName().setCertification("NONE");
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertThrows(UpdateNotAllowedException.class, executable);
    }


    @Test
    void savePersonDetails_notAllowed_notEnabled() {
        // given
        PersonDetails personDetails = new DummyPersonDetails();
        personConnector.save(personDetails);
        personConnector.deleteById(personDetails.getId());
        // when
        Executable executable = () -> personConnector.save(personDetails);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
    }


    @Test
    void deleteById() {
        //TODO
    }

    @Test
    void testDeleteById() {
        //TODO
    }

}