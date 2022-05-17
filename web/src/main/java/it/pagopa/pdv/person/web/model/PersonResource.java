package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class PersonResource extends SavePersonDto {

    @ApiModelProperty(value = "${swagger.model.person.id}", required = true)
    @NotNull
    private UUID id;

}
