package it.pagopa.pdv.person.connector.exception;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(Throwable cause) {
        super(cause);
    }
}

