package it.pagopa.pdv.person.core;

import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;

import java.util.Optional;

public interface PersonService {

    PersonDetailsOperations findById(String id, boolean isNamespaced, Optional<String> namespace);

    String findIdByNamespacedId(String namespacedId, String namespace);

    void save(PersonIdOperations personId);

    void save(PersonDetailsOperations personDetails);

    void deleteById(String id);

    void deleteById(String id, String namespace);
}
