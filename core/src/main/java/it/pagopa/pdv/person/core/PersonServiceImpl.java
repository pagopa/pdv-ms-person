package it.pagopa.pdv.person.core;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import it.pagopa.pdv.person.connector.PersonConnector;
import it.pagopa.pdv.person.connector.exception.ResourceNotFoundException;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonIdOperations;
import it.pagopa.pdv.person.core.logging.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
@XRayEnabled
class PersonServiceImpl implements PersonService {

    private static final String PERSON_ID_REQUIRED_MESSAGE = "A person id is required";

    private final PersonConnector personConnector;


    PersonServiceImpl(PersonConnector personConnector) {
        log.trace("Initializing {}", PersonServiceImpl.class.getSimpleName());
        this.personConnector = personConnector;
    }


    @Override
    public PersonDetailsOperations findById(String id, boolean isNamespaced, Optional<String> namespace) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, namespace = {}", id, namespace);
        Assert.hasText(id, PERSON_ID_REQUIRED_MESSAGE);
        Assert.notNull(namespace, "A not null namespace is required");
        if (isNamespaced) {
            id = findIdByNamespacedId(id, namespace.get());
        }
        PersonDetailsOperations person = personConnector.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "[findById] output = {}", person);
        log.trace("[findById] end");
        return person;
    }


    @Override
    public String findIdByNamespacedId(String namespacedId, String namespace) {
        log.trace("[findIdByNamespacedId] start");
        log.debug("[findIdByNamespacedId] inputs: namespacedId = {}, namespace = {}", namespacedId, namespace);
        Assert.hasText(namespacedId, "A person namespaced id is required");
        Assert.hasText(namespace, "A namespace is required");
        String id = personConnector.findIdByNamespacedId(namespacedId, namespace)
                .orElseThrow(ResourceNotFoundException::new);
        log.debug("[findIdByNamespacedId] output = {}", id);
        log.trace("[findIdByNamespacedId] end");
        return id;
    }


    @Override
    public void save(PersonIdOperations personId) {
        log.trace("[save] start");
        log.debug("[save] inputs: personId = {}", personId);
        Assert.notNull(personId, PERSON_ID_REQUIRED_MESSAGE);
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
        Assert.hasText(id, PERSON_ID_REQUIRED_MESSAGE);
        personConnector.deleteById(id);
        log.trace("[deleteById] end");
    }


    @Override
    public void deleteById(String id, String namespace) {
        log.trace("[deleteById] start");
        log.debug("[deleteById] inputs: id = {}, namespace = {}", id, namespace);
        Assert.hasText(id, PERSON_ID_REQUIRED_MESSAGE);
        Assert.hasText(namespace, "A namespace is required");
        personConnector.deleteById(id, namespace);
        log.trace("[deleteById] end");
    }

}
