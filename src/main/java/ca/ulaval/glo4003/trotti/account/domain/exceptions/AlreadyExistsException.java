package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String description) {
        super(description);
    }
}
