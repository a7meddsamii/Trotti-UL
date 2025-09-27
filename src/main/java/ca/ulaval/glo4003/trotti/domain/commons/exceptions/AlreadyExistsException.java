package ca.ulaval.glo4003.trotti.domain.commons.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String description) {
        super(description);
    }
}
