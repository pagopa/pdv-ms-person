package it.pagopa.pdv.person.web.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SavePersonNamespaceDto {

    @Schema(description = "${swagger.model.person.namespacedId}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID namespacedId;

}
