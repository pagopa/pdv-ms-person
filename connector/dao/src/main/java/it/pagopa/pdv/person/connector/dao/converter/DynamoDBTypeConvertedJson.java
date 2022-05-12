package it.pagopa.pdv.person.connector.dao.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.pagopa.pdv.person.connector.dao.model.DynamoDBCertifiableField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DynamoDBTypeConverted(converter = DynamoDBTypeConvertedJson.Converter.class)
@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface DynamoDBTypeConvertedJson {

    /**
     * The value type to use when calling the JSON mapper's {@code readValue};
     * a value of {@code Void.class} indicates to use the getter's type.
     */
    Class<? extends Object> targetType() default void.class;

    /**
     * JSON type converter.
     */
    final class Converter<T> implements DynamoDBTypeConverter<String, T> {
        private static final ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        private final Class<T> targetType;
        private final JavaType javaType;

        public Converter(Class<T> targetType, DynamoDBTypeConvertedJson annotation) {
            this.targetType = annotation.targetType() == void.class ? targetType : (Class<T>) annotation.targetType();
            if (targetType == DynamoDBCertifiableField.class) {
                if (annotation.targetType() != void.class) {
                    javaType = mapper.getTypeFactory().constructParametricType(targetType, annotation.targetType());
                } else {
                    javaType = null;
                }
            } else {
                javaType = null;
            }
        }

        @Override
        public final String convert(final T object) {
            try {
                return mapper.writeValueAsString(object);
            } catch (final Exception e) {
                throw new DynamoDBMappingException("Unable to write object to JSON", e);
            }
        }

        @Override
        public final T unconvert(final String object) {
            try {
                if (javaType != null) {
                    return mapper.readValue(object, javaType);
                } else {
                    return mapper.readValue(object, targetType);
                }
            } catch (final Exception e) {
                throw new DynamoDBMappingException("Unable to read JSON string", e);
            }
        }
    }

}
