package it.pagopa.pdv.person.web.validator;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Slf4j
@Aspect
@Component
public class PersonControllerResponseValidator {

    private final Validator validator;


    public PersonControllerResponseValidator(Validator validator) {
        log.trace("Initializing {}", PersonControllerResponseValidator.class.getSimpleName());
        Assert.notNull(validator, "Validator is required");
        this.validator = validator;
    }


    @AfterReturning(pointcut = "controllersPointcut()", returning = "result")
    public void validateResponse(JoinPoint joinPoint, Object result) {
        log.trace("[validateResponse] start");
        log.debug("[validateResponse] inputs: result = {}", result);
        if (result != null) {
            if (Collection.class.isAssignableFrom(result.getClass())) {
                ((Collection<?>) result).forEach(this::validate);
            } else {
                validate(result);
            }
        }
        log.trace("[validateResponse] end");
    }

    private void validate(Object result) {
        Set<ConstraintViolation<Object>> validationResults = validator.validate(result);
        if (validationResults.size() > 0) {
            Map<String, List<String>> errorMessage = new HashMap<>();
            validationResults.forEach((error) -> {
                String fieldName = error.getPropertyPath().toString();
                errorMessage.computeIfAbsent(fieldName, s -> new ArrayList<>())
                        .add(error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName() + " constraint violation");
            });
            throw new RuntimeException(errorMessage.toString());
        }
    }


    @Pointcut("execution(* it.pagopa.pdv.person.web.controller.*.*(..))")
    public void controllersPointcut() {
        // Do nothing because is a pointcut
    }

}