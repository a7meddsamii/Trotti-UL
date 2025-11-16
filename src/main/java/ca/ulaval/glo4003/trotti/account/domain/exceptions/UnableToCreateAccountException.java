package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class UnableToCreateAccountException extends RuntimeException {
    public UnableToCreateAccountException(String message) {
        super(message);
    }
}
