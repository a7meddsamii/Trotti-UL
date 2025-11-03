package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException() {
        this("Token has expired");
    }

    public ExpiredTokenException(String message) {
        super(message);
    }
}
