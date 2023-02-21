package it.pagopa.pdv.person.core;

import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.exception.ResourceNotFoundException;
import it.pagopa.pdv.person.connector.model.DummyPersonDetails;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonConnector personConnector;

    private static final String GLOBAL_NAMESPACE = "GLOBAL";


    @Test
    void findById_nullId() {
        // given
        String id = null;
        String namespace = "namespace";
        // when
        Executable executable = () -> personService.findById(id, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person id is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void findById_isNotNamespacedId() {
        // given
        String id = "id";
        String namespace = GLOBAL_NAMESPACE;
        PersonDetailsOperations personDetailsStub = new DummyPersonDetails();
        Mockito.when(personConnector.findById(Mockito.any()))
                .thenReturn(Optional.of(personDetailsStub));
        // when
        PersonDetailsOperations personDetails = personService.findById(id, namespace);
        // then
        assertSame(personDetailsStub, personDetails);
        Mockito.verify(personConnector, Mockito.times(1))
                .findById(id);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void findById_isNamespacedId() {
        // given
        String namespacedId = "namespacedId";
        String namespace = "namespace";
        PersonDetailsOperations personDetailsStub = new DummyPersonDetails();
        String id = "id";
        Mockito.when(personConnector.findIdByNamespacedId(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(id));
        Mockito.when(personConnector.findById(Mockito.any()))
                .thenReturn(Optional.of(personDetailsStub));
        // when
        PersonDetailsOperations personDetails = personService.findById(namespacedId, namespace);
        // then
        assertSame(personDetailsStub, personDetails);
        Mockito.verify(personConnector, Mockito.times(1))
                .findIdByNamespacedId(namespacedId, namespace);
        Mockito.verify(personConnector, Mockito.times(1))
                .findById(id);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void findById_isNotNamespacedId_notFound() {
        // given
        String id = "id";
        String namespace = GLOBAL_NAMESPACE;
        Mockito.when(personConnector.findById(Mockito.any()))
                .thenReturn(Optional.empty());
        // when
        Executable executable = () -> personService.findById(id, namespace);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(personConnector, Mockito.times(1))
                .findById(id);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void findIdByNamespacedId_nullId() {
        // given
        String namespacedId = null;
        String namespace = "namespace";
        // when
        Executable executable = () -> personService.findIdByNamespacedId(namespacedId,namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person namespaced id is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void findIdByNamespacedId_NotFound() {
        // given
        String namespacedId = "namespacedId";
        String namespace = "namespace";
        Mockito.when(personConnector.findIdByNamespacedId(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        // when
        Executable executable = () -> personService.findIdByNamespacedId(namespacedId,namespace);
        // then
        assertThrows(ResourceNotFoundException.class, executable);
        Mockito.verify(personConnector, Mockito.times(1))
                .findIdByNamespacedId(namespacedId, namespace);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void findIdByNamespacedId() {
        // given
        String namespacedId = "namespacedId";
        String namespace = "namespace";
        String idStub = "id";
        Mockito.when(personConnector.findIdByNamespacedId(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(idStub));
        // when
        String id = personService.findIdByNamespacedId(namespacedId,namespace);
        // then
        assertEquals(idStub, id);
        Mockito.verify(personConnector, Mockito.times(1))
                .findIdByNamespacedId(namespacedId, namespace);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void savePersonId_nullInput() {
        // given
        PersonIdOperations personId = null;
        // when
        Executable executable = () -> personService.save(personId);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person id is required", e.getMessage());
    }


    @Test
    void savePersonId() {
        // given
        PersonIdOperations personId = new PersonIdDto();
        // when
        personService.save(personId);
        // then
        Mockito.verify(personConnector, Mockito.times(1))
                .save(personId);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void savePersonDetails_nullInput() {
        // given
        PersonDetailsOperations personDetails = null;
        // when
        Executable executable = () -> personService.save(personDetails);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person details is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void savePersonDetails() {
        // given
        PersonDetailsOperations personDetails = new DummyPersonDetails();
        // when
        personService.save(personDetails);
        // then
        Mockito.verify(personConnector, Mockito.times(1))
                .save(personDetails);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void deleteById_nullId() {
        // given
        String id = null;
        // when
        Executable executable = () -> personService.deleteById(id);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person id is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void deleteById() {
        // given
        String id = "id";
        // when
        personService.deleteById(id);
        // then
        Mockito.verify(personConnector, Mockito.times(1))
                .deleteById(id);
        Mockito.verifyNoMoreInteractions(personConnector);
    }


    @Test
    void deleteNamespacedPerson_nullId() {
        // given
        String id = null;
        String namespace = "selfcare";
        // when
        Executable executable = () -> personService.deleteById(id, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A person id is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void deleteNamespacedPerson_nullNamespace() {
        // given
        String id = "id";
        String namespace = null;
        // when
        Executable executable = () -> personService.deleteById(id, namespace);
        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("A namespace is required", e.getMessage());
        Mockito.verifyNoInteractions(personConnector);
    }


    @Test
    void deleteNamespacedPerson() {
        // given
        String id = "id";
        String namespace = "selfcare";
        // when
        personService.deleteById(id, namespace);
        // then
        Mockito.verify(personConnector, Mockito.times(1))
                .deleteById(id, namespace);
        Mockito.verifyNoMoreInteractions(personConnector);
    }

}