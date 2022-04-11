package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class SavePersonDto {

    @ApiModelProperty(value = "${swagger.model.person.givenName}")
    private String givenName;
    @ApiModelProperty(value = "${swagger.model.person.familyName}")
    private String familyName;
    @ApiModelProperty(value = "${swagger.model.person.workContacts}")
    private Map<String, WorkContactResource> workContacts;

}
