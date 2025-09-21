package ca.ulaval.glo4003.trotti.domain.account.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
