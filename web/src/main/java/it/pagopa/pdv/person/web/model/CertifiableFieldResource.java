package it.pagopa.pdv.person.web.model;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "${swagger.model.certifiableField.certification}", required = true)
    @NotBlank
    private String certification;

    @ApiModelProperty(value = "${swagger.model.certifiableField.value}", required = true)
    @NotNull
    private T value;

}
