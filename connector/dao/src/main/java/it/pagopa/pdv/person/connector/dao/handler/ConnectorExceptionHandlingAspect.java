package it.pagopa.pdv.person.connector.dao.handler;

import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException;
import it.pagopa.pdv.person.connector.exception.TooManyRequestsException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ConnectorExceptionHandlingAspect {

    @AfterThrowing(pointcut = "execution(* it.pagopa.pdv.person.connector.dao.*.*(..))", throwing = "ex")
    public void handleProvisionedThroughputExceededExceptionCall(ProvisionedThroughputExceededException ex){
        log.trace("[ConnectorExceptionHandlingAspect] handleProvisionedThroughputExceededExceptionCall");
        throw new TooManyRequestsException(ex);
    }

}
