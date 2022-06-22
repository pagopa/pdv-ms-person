package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import lombok.Data;

import javax.validation.Valid;

@Data
public class WorkContactResource implements PersonDetailsOperations.WorkContactOperations {

    @ApiModelProperty(value = "${swagger.model.person.workContact.email}")
    @Valid
    private CertifiableFieldResource<String> email;

}
