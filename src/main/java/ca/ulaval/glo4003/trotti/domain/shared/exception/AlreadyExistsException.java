package ca.ulaval.glo4003.trotti.domain.shared.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String description) {
        super(description);
    }
}
