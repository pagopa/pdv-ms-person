package it.pagopa.pdv.person.web.exception;

public class ResponseValidationException extends RuntimeException {

    public ResponseValidationException(String message) {
        super(message);
    }

}
