package it.pagopa.pdv.person.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.model.*;
import it.pagopa.pdv.person.web.model.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static it.pagopa.pdv.person.core.logging.LogUtils.CONFIDENTIAL_MARKER;

@Slf4j
@RestController
@RequestMapping(value = "people", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "person")
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        log.trace("Initializing {}", PersonController.class.getSimpleName());
        this.personService = personService;
    }


    @ApiOperation(value = "${swagger.api.person.findById.summary}",
            notes = "${swagger.api.person.findById.notes}")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResource findById(
            @ApiParam("${swagger.model.person.id}")
            @PathVariable("id")
            UUID id,
            @ApiParam("${swagger.model.namespace}")
            @RequestParam(value = "namespace", required = false)
            Optional<String> namespace) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, namespace = {}", id, namespace);
        PersonDetailsOperations personDetailsOperations = personService.findById(id.toString(), namespace);
        PersonResource personResource = PersonMapper.toResource(personDetailsOperations);
        log.debug(CONFIDENTIAL_MARKER, "[findById] output = {}", personResource);
        log.trace("[findById] end");
        return personResource;
    }


    @ApiOperation(value = "${swagger.api.person.findIdByNamespacedId.summary}",
            notes = "${swagger.api.person.findIdByNamespacedId.notes}")
    @GetMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public PersonId findIdByNamespacedId(@ApiParam("${swagger.model.person.namespacedId}")
                                         @RequestParam("namespacedId")
                                         UUID namespacedId,
                                         @ApiParam("${swagger.model.namespace}")
                                         @RequestParam("namespace")
                                         String namespace) {
        log.trace("[findIdByNamespacedId] start");
        log.debug("[findIdByNamespacedId] inputs: namespacedId = {}, namespace = {}", namespacedId, namespace);
        String id = personService.findIdByNamespacedId(namespacedId.toString(), namespace);
        PersonId personId = new PersonId();
        personId.setId(UUID.fromString(id));
        log.debug("[findIdByNamespacedId] output = {}", personId);
        log.trace("[findIdByNamespacedId] end");
        return personId;
    }


    @ApiOperation(value = "${swagger.api.person.saveNamespacedId.summary}",
            notes = "${swagger.api.person.saveNamespacedId.notes}")
    @PutMapping(value = "{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveNamespacedId(@ApiParam("${swagger.model.person.id}")
                                 @PathVariable("id")
                                 UUID id,
                                 @ApiParam("${swagger.model.namespace}")
                                 @PathVariable("namespace")
                                 String namespace,
                                 @RequestBody
                                 @Valid
                                 SavePersonNamespaceDto request) {
        log.trace("[saveNamespacedId] start");
        log.debug("[saveNamespacedId] inputs: id = {}, namespace = {}, request = {}", id, namespace, request);
        PersonIdDto personId = PersonMapper.assembles(id, namespace, request);
        personService.save(personId);
        log.trace("[saveNamespacedId] end");
    }


    @ApiOperation(value = "${swagger.api.person.save.summary}",
            notes = "${swagger.api.person.save.notes}")
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    public void save(@ApiParam("${swagger.model.person.id}")
                     @PathVariable("id")
                     UUID id,
                     @RequestBody
                     @Valid
                     SavePersonDto request) {
        log.trace("[save] start");
        log.debug(CONFIDENTIAL_MARKER, "[save] inputs: id = {}, request = {}", id, request);
        PersonDto personDto = PersonMapper.assembles(id, request);
        personService.save(personDto);
        log.trace("[save] end");
    }


    @ApiOperation(value = "${swagger.api.person.deletePerson.summary}",
            notes = "${swagger.api.person.deletePerson.notes}")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@ApiParam("${swagger.model.person.id}")
                             @PathVariable("id")
                             UUID id) {
        log.trace("[deletePerson] start");
        log.debug("[deletePerson] inputs: id = {}", id);
        personService.deleteById(id.toString());
        log.trace("[deletePerson] end");
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
        log.trace("[deletePersonNamespace] start");
        log.debug("[deletePersonNamespace] inputs: id = {}, namespace = {}", id, namespace);
        personService.deleteById(id.toString(), namespace);
        log.trace("[deletePersonNamespace] end");
    }

}
