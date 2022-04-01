package it.pagopa.pdv.person.core;

import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
class PersonServiceImpl implements PersonService {

    private final PersonConnector personConnector;


    PersonServiceImpl(PersonConnector personConnector) {
        this.personConnector = personConnector;
    }


    @Override
    public PersonDetailsOperations findById(String id, boolean isNamespaced) {
        Optional<PersonDetailsOperations> person;
        if (isNamespaced) {
            person = personConnector.findByNamespacedId(id);
        } else {
            person = personConnector.findByGlobalId(id);
        }
        return person.orElseThrow();//FIXME: exception type
    }


    @Override
    public void save(PersonDetailsOperations personDetails) {
        personConnector.save(personDetails);
    }


    @Override
    public void save(PersonIdOperations personId) {
        personConnector.save(personId);
    }


    @Override
    public void patch(PersonDetailsOperations personDetails) {
        personConnector.patch(personDetails);
    }


    @Override
    public void deleteById(String id) {
        personConnector.deleteById(id);
    }

    @Override
    public void deleteById(String id, String namespace) {
        personConnector.deleteById(id, namespace);
    }

}
