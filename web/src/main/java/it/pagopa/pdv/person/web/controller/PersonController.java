package it.pagopa.pdv.person.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.core.PersonService;
import it.pagopa.pdv.person.web.annotations.CommonApiResponsesWrapper;
import it.pagopa.pdv.person.web.model.*;
import it.pagopa.pdv.person.web.model.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static it.pagopa.pdv.person.core.logging.LogUtils.CONFIDENTIAL_MARKER;

@Slf4j
@RestController
@RequestMapping(value = "people", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "person")
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        log.trace("Initializing {}", PersonController.class.getSimpleName());
        this.personService = personService;
    }


    @Operation(summary = "${swagger.api.person.findById.summary}",
            description = "${swagger.api.person.findById.notes}")
    @CommonApiResponsesWrapper
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResource findById(
            @Parameter(description="${swagger.model.person.id}")
            @PathVariable("id")
            UUID id,
            @Parameter(description="${swagger.model.person.isNamespaced}")
            @RequestParam("isNamespaced")
            boolean isNamespaced,
            @Parameter(description="${swagger.model.namespace}")
            @RequestParam(value = "namespace", required = false)
            Optional<String> namespace) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, namespace = {}, isNamespaced = {}", id, namespace, isNamespaced);
        PersonDetailsOperations personDetailsOperations = personService.findById(id.toString(), isNamespaced, namespace);
        PersonResource personResource = PersonMapper.toResource(personDetailsOperations);
        log.debug(CONFIDENTIAL_MARKER, "[findById] output = {}", personResource);
        log.trace("[findById] end");
        return personResource;
    }


    @Operation(summary = "${swagger.api.person.findIdByNamespacedId.summary}",
            description = "${swagger.api.person.findIdByNamespacedId.notes}")
    @CommonApiResponsesWrapper
    @GetMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public PersonId findIdByNamespacedId(@Parameter(description="${swagger.model.person.namespacedId}")
                                         @RequestParam("namespacedId")
                                         UUID namespacedId,
                                         @Parameter(description="${swagger.model.namespace}")
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


    @Operation(summary = "${swagger.api.person.saveNamespacedId.summary}",
            description = "${swagger.api.person.saveNamespacedId.notes}")
    @CommonApiResponsesWrapper
    @PutMapping(value = "{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveNamespacedId(@Parameter(description="${swagger.model.person.id}")
                                 @PathVariable("id")
                                 UUID id,
                                 @Parameter(description="${swagger.model.namespace}")
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


    @Operation(summary = "${swagger.api.person.save.summary}",
            description = "${swagger.api.person.save.notes}")
    @CommonApiResponsesWrapper
    @ApiResponse(responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@Parameter(description="${swagger.model.person.id}")
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


    @Operation(summary = "${swagger.api.person.deletePerson.summary}",
            description = "${swagger.api.person.deletePerson.notes}")
    @CommonApiResponsesWrapper
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@Parameter(description="${swagger.model.person.id}")
                             @PathVariable("id")
                             UUID id) {
        log.trace("[deletePerson] start");
        log.debug("[deletePerson] inputs: id = {}", id);
        personService.deleteById(id.toString());
        log.trace("[deletePerson] end");
    }


    @Operation(summary = "${swagger.api.person.deletePersonNamespace.summary}",
            description = "${swagger.api.person.deletePersonNamespace.notes}")
    @CommonApiResponsesWrapper
    @DeleteMapping("{id}/namespace/{namespace}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonNamespace(@Parameter(description="${swagger.model.person.id}")
                                      @PathVariable("id")
                                      UUID id,
                                      @Parameter(description="${swagger.model.namespace}")
                                      @PathVariable("namespace")
                                      String namespace) {
        log.trace("[deletePersonNamespace] start");
        log.debug("[deletePersonNamespace] inputs: id = {}, namespace = {}", id, namespace);
        personService.deleteById(id.toString(), namespace);
        log.trace("[deletePersonNamespace] end");
    }

}
