package it.pagopa.pdv.person.web.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

@Data
public class SavePersonDto {

    @Schema(ref = "NameCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> name;

    @Schema(ref = "FamilyNameCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> familyName;

    @Schema(ref = "EmailCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> email;

    @Schema(ref = "BirthDateCertifiableSchema")
    @Valid
    private CertifiableFieldResource<LocalDate> birthDate;

    @Schema(description = "${swagger.model.person.workContacts}")
    @Valid
    private Map<String, WorkContactResource> workContacts;

}
