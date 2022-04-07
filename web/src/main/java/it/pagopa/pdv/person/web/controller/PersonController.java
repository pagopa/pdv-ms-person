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
        PersonResource personResource = PersonMapper.toResource(personDetailsOperations);
        return personResource;
    }


    @ApiOperation(value = "${swagger.people.api.saveDetails.summary}",
            notes = "${swagger.people.api.saveDetails.notes}")
    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Deprecated
    public void saveDetails(@ApiParam("${swagger.model.person.id}")
                            @PathVariable("id")
                                    UUID id,
                            @RequestBody
                                    SavePersonDto request) {
        PersonDto personDto = PersonMapper.assembles(id, request);
        personService.save(personDto);
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
        PersonIdDto personId = PersonMapper.assembles(id, namespace, request);
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
        PersonDto personDto = PersonMapper.assembles(id, request);
        personService.patch(personDto);
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
