package it.pagopa.pdv.person.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSearchDto {

    @ApiModelProperty(value = "${swagger.user-registry.users.model.externalId}", required = true)
    @JsonProperty(required = true)
    @NotBlank
    private String externalId;

}
