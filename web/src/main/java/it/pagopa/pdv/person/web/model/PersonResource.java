package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
public class PersonResource {

    @ApiModelProperty(value = "${swagger.model.person.id}", required = true)
    @NotNull
    private UUID id;
    @ApiModelProperty(value = "${swagger.model.person.name}")
    private CertifiableFieldResource<String> name;
    @ApiModelProperty(value = "${swagger.model.person.familyName}")
    private CertifiableFieldResource<String> familyName;
    @ApiModelProperty(value = "${swagger.model.person.email}")
    private CertifiableFieldResource<String> email;
    @ApiModelProperty(value = "${swagger.model.person.birthDate}")
    private CertifiableFieldResource<LocalDate> birthDate;
    @ApiModelProperty(value = "${swagger.model.person.workContacts}")
    private Map<String, WorkContactResource> workContacts;

}
