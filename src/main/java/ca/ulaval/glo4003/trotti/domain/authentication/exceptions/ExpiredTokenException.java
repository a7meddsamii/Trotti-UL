package ca.ulaval.glo4003.trotti.domain.authentication.exceptions;

public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException() {
        this("Token has expired");
    }

    public ExpiredTokenException(String message) {
        super(message);
    }
}
