package ca.ulaval.glo4003.trotti.domain.shared.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String description) {
        super(description);
    }
}
