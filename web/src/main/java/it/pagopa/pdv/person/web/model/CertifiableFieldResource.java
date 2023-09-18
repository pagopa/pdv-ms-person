package it.pagopa.pdv.person.web.model;


import io.swagger.v3.oas.annotations.media.Schema;
import it.pagopa.pdv.person.connector.model.CertifiableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CertifiableFieldResource<T> implements CertifiableField<T> {

    public CertifiableFieldResource(CertifiableField<T> certifiableField) {
        Assert.notNull(certifiableField, "A CertifiableField is required");
        certification = certifiableField.getCertification();
        value = certifiableField.getValue();
    }

    @Schema(description = "${swagger.model.certifiableField.certification}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String certification;

    @Schema(description = "${swagger.model.certifiableField.value}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private T value;

}
