package it.pagopa.pdv.person.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class PersonResource extends SavePersonDto {

    @Schema(description = "${swagger.model.person.id}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID id;

}
