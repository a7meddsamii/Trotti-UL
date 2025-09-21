package ca.ulaval.glo4003.trotti.domain.shared.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String description) {
        super(description);
    }
}
