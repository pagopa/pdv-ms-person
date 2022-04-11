package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class PersonId {

    @ApiModelProperty(value = "${swagger.model.person.id}", required = true)
    @NotEmpty
    private UUID id;

}
