package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Data
public class PersonResource {

    @ApiModelProperty(value = "${swagger.model.person.id}", required = true)
    @NotNull
    private UUID id;
    @ApiModelProperty(value = "${swagger.model.person.givenName}")
    private String givenName;
    @ApiModelProperty(value = "${swagger.model.person.familyName}")
    private String familyName;
    @ApiModelProperty(value = "${swagger.model.person.workContacts}")
    private Map<String, WorkContactResource> workContacts;
    //TODO certification field

}
