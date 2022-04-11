package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class SavePersonNamespaceDto {

    @ApiModelProperty(value = "${swagger.model.person.namespacedId}", required = true)
    @NotEmpty
    private UUID namespacedId;

}
