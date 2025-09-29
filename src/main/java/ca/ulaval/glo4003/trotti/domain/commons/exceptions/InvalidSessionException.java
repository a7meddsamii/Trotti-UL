package ca.ulaval.glo4003.trotti.domain.commons.exceptions;

public class InvalidSessionException extends RuntimeException {

    public InvalidSessionException(String message) {
        super("INVALID_SESSION_EXCEPTION: " + message);
    }
}
