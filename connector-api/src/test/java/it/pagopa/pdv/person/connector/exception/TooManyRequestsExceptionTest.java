package it.pagopa.pdv.person.connector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TooManyRequestsExceptionTest {

    @Test
    public void testConstructorWithCause() {
        Exception cause = new Exception("Some cause");

        TooManyRequestsException exception = new TooManyRequestsException(cause);

        assertEquals(cause, exception.getCause(), "Cause should be set correctly.");

    }
}
