package it.pagopa.pdv.person.web.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import it.pagopa.pdv.person.web.model.CertifiableFieldResource;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;


@Slf4j
@Configuration
class SwaggerConfig {

    @Configuration
    @Profile("swaggerIT")
    @PropertySource("classpath:/swagger/swagger_it.properties")
    public static class ItConfig {
    }

    @Configuration
    @Profile("swaggerEN")
    @PropertySource("classpath:/swagger/swagger_en.properties")
    public static class EnConfig {
    }

    private final Environment environment;


    @Autowired
    SwaggerConfig(Environment environment) {
        log.trace("Initializing {}", SwaggerConfig.class.getSimpleName());
        Assert.notNull(environment, "Environment is required");
        this.environment = environment;
    }

    private static class CertifiableFieldResourceString extends CertifiableFieldResource<String>{
    }
    private static class CertifiableFieldResourceDate extends CertifiableFieldResource<LocalDate>{
    }

    @Bean
    public OpenAPI swaggerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(environment.getProperty("swagger.title", environment.getProperty("spring.application.name")))
                        .description(environment.getProperty("swagger.description", "Api and Models"))
                        .version(environment.getProperty("swagger.version", environment.getProperty("spring.application.version"))))
                .components(new Components()
                        .addSchemas("NameCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.person.name}" ))
                        .addSchemas("FamilyNameCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.person.familyName}" ))
                        .addSchemas("EmailCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.person.email}" ))
                    .addSchemas("PhoneCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.person.workContact.phone}" ))
                    .addSchemas("BirthDateCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceDate.class, "${swagger.model.person.birthDate}" )));

    }


    private Schema getSchemaWithDifferentDescription(Class className, String description){
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(className).resolveAsRef(false));
        return resolvedSchema.schema.description(description);
    }

}

