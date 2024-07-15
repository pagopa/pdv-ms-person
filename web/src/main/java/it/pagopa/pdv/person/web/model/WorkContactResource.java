package it.pagopa.pdv.person.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class WorkContactResource implements PersonDetailsOperations.WorkContactOperations {

    @Schema(ref = "EmailCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> email;

    @Schema(ref = "PhoneCertifiableSchema")
    @Valid
    private CertifiableFieldResource<String> phone;

}
