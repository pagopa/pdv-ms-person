package it.pagopa.pdv.person.web.model.mapper;

import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.web.model.PersonResource;
import it.pagopa.pdv.person.web.model.SavePersonDto;
import it.pagopa.pdv.person.web.model.SavePersonNamespaceDto;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonIdDto assembles(UUID id, String namespace, SavePersonNamespaceDto savePersonNamespaceDto) {
        PersonIdDto personIdDto = new PersonIdDto();
        personIdDto.setNamespace(namespace);
        if (id != null) {
            personIdDto.setGlobalId(id.toString());
        }
        if (savePersonNamespaceDto != null && savePersonNamespaceDto.getNamespacedId() != null) {
            personIdDto.setNamespacedId(savePersonNamespaceDto.getNamespacedId().toString());
        }
        return personIdDto;
    }


    public static PersonDto assembles(UUID id, SavePersonDto savePersonDto) {
        PersonDto personDto = null;
        if (savePersonDto != null) {
            personDto = new PersonDto();
            personDto.setId(id.toString());
            personDto.setGivenName(savePersonDto.getGivenName());
            personDto.setFamilyName(savePersonDto.getFamilyName());
            if (savePersonDto.getWorkContacts() != null) {
                personDto.setWorkContacts(savePersonDto.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), toDto(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return personDto;
    }


    static PersonDto.WorkContactDto toDto(PersonResource.WorkContactResource workContactResource) {
        PersonDto.WorkContactDto workContactDto = null;
        if (workContactResource != null) {
            workContactDto = new PersonDto.WorkContactDto();
            workContactDto.setEmail(workContactResource.getEmail());
        }
        return workContactDto;
    }


    public static PersonResource toResource(PersonDetailsOperations personDetailsOperations) {
        PersonResource personResource = null;
        if (personDetailsOperations != null) {
            personResource = new PersonResource();
            personResource.setId(personDetailsOperations.getId());
            personResource.setGivenName(personDetailsOperations.getGivenName());
            personResource.setFamilyName(personDetailsOperations.getFamilyName());
            if (personDetailsOperations.getWorkContacts() != null) {
                personResource.setWorkContacts(personDetailsOperations.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), toResource(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return personResource;
    }


    static PersonResource.WorkContactResource toResource(PersonDetailsOperations.WorkContactOperations workContactOperations) {
        PersonResource.WorkContactResource workContactResource = null;
        if (workContactOperations != null) {
            workContactResource = new PersonResource.WorkContactResource();
            workContactResource.setEmail(workContactOperations.getEmail());
        }
        return workContactResource;
    }

}
