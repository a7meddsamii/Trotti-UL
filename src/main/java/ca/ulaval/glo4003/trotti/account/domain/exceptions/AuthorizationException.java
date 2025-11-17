package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
