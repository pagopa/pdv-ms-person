package it.pagopa.pdv.person.core;

import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;

public interface PersonService {

    PersonDetailsOperations findById(String id, boolean isNamespaced);

    String findIdByNamespacedId(String id);

    void save(PersonDetailsOperations personDetails);

    void save(PersonIdOperations personId);

    void patch(PersonDetailsOperations personDetails);

    void deleteById(String id);

    void deleteById(String id, String namespace);
}
