package it.pagopa.pdv.person.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.model.PersonId;
import it.pagopa.pdv.person.web.model.PersonResource;
import it.pagopa.pdv.person.web.model.SavePersonDto;
import it.pagopa.pdv.person.web.model.SavePersonNamespaceDto;
import it.pagopa.pdv.person.web.model.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "people", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "person")
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @ApiOperation(value = "${swagger.api.person.findById.summary}",
            notes = "${swagger.api.person.findById.notes}")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResource findById(@ApiParam("${swagger.model.person.id}")
                                   @PathVariable("id")
                                           UUID id,
                                   @ApiParam("${swagger.model.person.isNamespaced}")
                                   @RequestParam("isNamespaced")
                                           boolean isNamespaced) {
        PersonDetailsOperations personDetailsOperations = personService.findById(id.toString(), isNamespaced);
        PersonResource personResource = PersonMapper.toResource(personDetailsOperations);
        return personResource;
    }


    @ApiOperation(value = "${swagger.api.person.findIdByNamespacedId.summary}",
            notes = "${swagger.api.person.findIdByNamespacedId.notes}")
    @GetMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public PersonId findIdByNamespacedId(@ApiParam("${swagger.model.person.namespacedId}")
                                         @RequestParam("namespacedId")
                                                 UUID namespacedId) {
        String id = personService.findIdByNamespacedId(namespacedId.toString());
        PersonId personId = new PersonId();
        personId.setId(UUID.fromString(id));
        return personId;
    }


    @ApiOperation(value = "${swagger.api.person.saveNamespacedId.summary}",
            notes = "${swagger.api.person.saveNamespacedId.notes}")
    @PutMapping(value = "{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.OK)
    public void saveNamespacedId(@ApiParam("${swagger.model.person.id}")
                                 @PathVariable("id")
                                         UUID id,
                                 @ApiParam("${swagger.model.namespace}")
                                 @PathVariable("namespace")
                                         String namespace,
                                 @RequestBody
                                         SavePersonNamespaceDto request) {
        PersonIdDto personId = PersonMapper.assembles(id, namespace, request);
        personService.save(personId);
    }


    @ApiOperation(value = "${swagger.api.person.save.summary}",
            notes = "${swagger.api.person.save.notes}")
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void save(@ApiParam("${swagger.model.person.id}")
                     @PathVariable("id")
                             UUID id,
                     @RequestBody
                             SavePersonDto request) {
        PersonDto personDto = PersonMapper.assembles(id, request);
        personService.patch(personDto);
    }


    @ApiOperation(value = "${swagger.api.person.deletePerson.summary}",
            notes = "${swagger.api.person.deletePerson.notes}")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@ApiParam("${swagger.model.person.id}")
                             @PathVariable("id")
                                     UUID id) {
        personService.deleteById(id.toString());
    }


    @ApiOperation(value = "${swagger.api.person.deletePersonNamespace.summary}",
            notes = "${swagger.api.person.deletePersonNamespace.notes}")
    @DeleteMapping("{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonNamespace(@ApiParam("${swagger.model.person.id}")
                                      @PathVariable("id")
                                              UUID id,
                                      @ApiParam("${swagger.model.namespace}")
                                      @PathVariable("namespace")
                                              String namespace) {
        personService.deleteById(id.toString(), namespace);
    }

}
