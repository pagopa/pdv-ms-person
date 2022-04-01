package it.pagopa.pdv.person.connector;

import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;

import java.util.Optional;

public interface PersonConnector {

    Optional<PersonDetailsOperations> findByGlobalId(String id);

    Optional<PersonDetailsOperations> findByNamespacedId(String id);

    void save(PersonDetailsOperations personDetails);

    void patch(PersonDetailsOperations personDetails);

    void save(PersonIdOperations personId);

    void deleteById(String id);

    void deleteById(String id, String namespace);
}
