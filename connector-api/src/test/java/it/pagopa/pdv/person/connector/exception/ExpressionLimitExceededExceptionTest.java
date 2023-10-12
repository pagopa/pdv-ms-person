package it.pagopa.pdv.person.connector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ExpressionLimitExceededExceptionTest {

    @Test
    public void testConstructorWithCause() {

        ExpressionLimitExceededException exception = new ExpressionLimitExceededException();

        assertNotNull(exception);
    }
}
