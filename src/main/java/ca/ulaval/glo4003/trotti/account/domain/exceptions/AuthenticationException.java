package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
