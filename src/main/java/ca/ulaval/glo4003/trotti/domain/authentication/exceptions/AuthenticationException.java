package ca.ulaval.glo4003.trotti.domain.authentication.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
