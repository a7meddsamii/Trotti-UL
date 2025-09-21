package ca.ulaval.glo4003.trotti.domain.account.exception;

public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException() {
        this("Token has expired");
    }

    public ExpiredTokenException(String message) {
        super(message);
    }
}
