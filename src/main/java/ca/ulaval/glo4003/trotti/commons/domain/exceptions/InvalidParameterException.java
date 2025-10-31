package ca.ulaval.glo4003.trotti.commons.domain.exceptions;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String description) {
        super(description);
    }
}
