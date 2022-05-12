package it.pagopa.pdv.person.core;

import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.exception.ResourceNotFoundException;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import it.pagopa.pdv.person.core.logging.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
class PersonServiceImpl implements PersonService {

    private final PersonConnector personConnector;


    PersonServiceImpl(PersonConnector personConnector) {
        log.trace("Initializing {}", PersonServiceImpl.class.getSimpleName());
        this.personConnector = personConnector;
    }


    @Override
    public PersonDetailsOperations findById(String id, boolean isNamespaced) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, isNamespaced = {}", id, isNamespaced);
        Assert.hasText(id, "A person id is required");
        if (isNamespaced) {
            id = findIdByNamespacedId(id);
        }
        PersonDetailsOperations person = personConnector.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[findById] output = {}", person);
        log.trace("[findById] end");
        return person;
    }


    @Override
    public String findIdByNamespacedId(String namespacedId) {
        log.trace("[findIdByNamespacedId] start");
        log.debug("[findIdByNamespacedId] inputs: namespacedId = {}", namespacedId);
        Assert.hasText(namespacedId, "A person namespaced id is required");
        String id = personConnector.findIdByNamespacedId(namespacedId)
                .orElseThrow(ResourceNotFoundException::new);
        log.debug("[findIdByNamespacedId] output = {}", id);
        log.trace("[findIdByNamespacedId] end");
        return id;
    }


    @Override
    public void save(PersonIdOperations personId) {
        log.trace("[save] start");
        log.debug("[save] inputs: personId = {}", personId);
        Assert.notNull(personId, "A person id is required");
        personConnector.save(personId);
        log.trace("[save] end");
    }


    @Override
    public void save(PersonDetailsOperations personDetails) {
        log.trace("[save] start");
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[save] inputs: personDetails = {}", personDetails);
        Assert.notNull(personDetails, "A person details is required");
        personConnector.save(personDetails);
        log.trace("[save] end");
    }


    @Override
    public void deleteById(String id) {
        log.trace("[deleteById] start");
        log.debug("[deleteById] inputs: id = {}", id);
        Assert.hasText(id, "A person id is required");
        personConnector.deleteById(id);
        log.trace("[deleteById] end");
    }


    @Override
    public void deleteById(String id, String namespace) {
        log.trace("[deleteById] start");
        log.debug("[deleteById] inputs: id = {}, namespace = {}", id, namespace);
        Assert.hasText(id, "A person id is required");
        Assert.hasText(namespace, "A namespace is required");
        personConnector.deleteById(id, namespace);
        log.trace("[deleteById] end");
    }

}
