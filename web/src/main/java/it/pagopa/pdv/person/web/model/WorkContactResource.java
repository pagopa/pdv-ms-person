package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkContactResource {

    @ApiModelProperty(value = "${swagger.model.person.workContact.email}")
    private String email;

}
