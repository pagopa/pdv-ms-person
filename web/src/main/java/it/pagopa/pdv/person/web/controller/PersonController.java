package it.pagopa.pdv.person.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.model.PersonResource;
import it.pagopa.pdv.person.web.model.SavePersonDto;
import it.pagopa.pdv.person.web.model.SavePersonNamespaceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "people", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "people")
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @ApiOperation(value = "${swagger.people.api.getUserById.summary}",
            notes = "${swagger.people.api.getUserById.notes}")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResource getUserById(@ApiParam("${swagger.model.person.id}")
                                      @PathVariable("id")
                                              UUID id,
                                      @ApiParam("${swagger.model.person.isNamespaced}")
                                      @RequestParam("isNamespaced")
                                              boolean isNamespaced) {
        PersonDetailsOperations personDetailsOperations = personService.findById(id.toString(), isNamespaced);
        PersonResource personResource = new PersonResource();
        personResource.setId(personDetailsOperations.getId());
        personResource.setGivenName(personDetailsOperations.getGivenName());
        personResource.setFamilyName(personDetailsOperations.getFamilyName());
        if (personDetailsOperations.getWorkContacts() != null) {
            HashMap<String, PersonResource.WorkContactResource> workContacts = new HashMap<>(personDetailsOperations.getWorkContacts().size());
            personDetailsOperations.getWorkContacts().forEach((s, wc) -> {
                PersonResource.WorkContactResource workContact = new PersonResource.WorkContactResource();
                workContact.setEmail(wc.getEmail());
                workContacts.put(s, workContact);
            });
            personResource.setWorkContacts(workContacts);
        }
        return personResource;
    }


    @ApiOperation(value = "${swagger.people.api.saveDetails.summary}",
            notes = "${swagger.people.api.saveDetails.notes}")
    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveDetails(@ApiParam("${swagger.model.person.id}")
                            @PathVariable("id")
                                    UUID id,
                            @RequestBody
                                    SavePersonDto request) {
        PersonDto person = new PersonDto();
        person.setId(id.toString());
        person.setGivenName(request.getGivenName());
        person.setFamilyName(request.getFamilyName());
        if (request.getWorkContacts() != null) {
            HashMap<String, PersonDto.WorkContactDto> workContacts = new HashMap<>(request.getWorkContacts().size());
            request.getWorkContacts().forEach((s, wc) -> {
                PersonDto.WorkContactDto workContact = new PersonDto.WorkContactDto();
                workContact.setEmail(wc.getEmail());
                workContacts.put(s, workContact);
            });
            person.setWorkContacts(workContacts);
        }
        personService.save(person);
    }


    @ApiOperation(value = "${swagger.people.api.saveNamespacedId.summary}",
            notes = "${swagger.people.api.saveNamespacedId.notes}")
    @PutMapping(value = "{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.OK)
    public void saveNamespacedId(@ApiParam("${swagger.model.person.id}")
                                 @PathVariable("id")
                                         UUID id,
                                 @ApiParam("${swagger.model.person.namespace}")
                                 @PathVariable("namespace")
                                         String namespace,
                                 @RequestBody
                                         SavePersonNamespaceDto request) {
        PersonIdDto personId = new PersonIdDto();
        personId.setGlobalId(id.toString());
        personId.setNamespace(namespace);
        personId.setNamespacedId(request.getNamespacedId().toString());
        personService.save(personId);
    }


    @ApiOperation(value = "${swagger.people.api.patchSave.summary}",
            notes = "${swagger.people.api.patchSave.notes}")
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchSave(@ApiParam("${swagger.model.person.id}")
                          @PathVariable("id")
                                  UUID id,
                          @RequestBody
                                  SavePersonDto request) {
        PersonDto person = new PersonDto();
        person.setId(id.toString());
        person.setGivenName(request.getGivenName());
        person.setFamilyName(request.getFamilyName());
        if (request.getWorkContacts() != null) {
            HashMap<String, PersonDto.WorkContactDto> workContacts = new HashMap<>(request.getWorkContacts().size());
            request.getWorkContacts().forEach((s, wc) -> {
                PersonDto.WorkContactDto workContact = new PersonDto.WorkContactDto();
                workContact.setEmail(wc.getEmail());
                workContacts.put(s, workContact);
            });
            person.setWorkContacts(workContacts);
        }
        personService.patch(person);
    }


    @ApiOperation(value = "${swagger.people.api.deletePerson.summary}",
            notes = "${swagger.people.api.deletePerson.notes}")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@ApiParam("${swagger.model.person.id}")
                             @PathVariable("id")
                                     UUID id) {
        personService.deleteById(id.toString());
    }


    @ApiOperation(value = "${swagger.people.api.deletePersonNamespace.summary}",
            notes = "${swagger.people.api.deletePersonNamespace.notes}")
    @DeleteMapping("{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonNamespace(@ApiParam("${swagger.model.person.id}")
                                      @PathVariable("id")
                                              UUID id,
                                      @ApiParam("${swagger.model.person.namespace}")
                                      @PathVariable("namespace")
                                              String namespace) {
        personService.deleteById(id.toString(), namespace);
    }

}
