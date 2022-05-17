package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class SavePersonDto {

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
