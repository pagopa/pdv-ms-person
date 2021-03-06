package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Data
public class SavePersonDto {

    @ApiModelProperty(value = "${swagger.model.person.name}")
    @Valid
    private CertifiableFieldResource<String> name;

    @ApiModelProperty(value = "${swagger.model.person.familyName}")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @ApiModelProperty(value = "${swagger.model.person.email}")
    @Valid
    private CertifiableFieldResource<String> email;

    @ApiModelProperty(value = "${swagger.model.person.birthDate}")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @ApiModelProperty(value = "${swagger.model.person.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
