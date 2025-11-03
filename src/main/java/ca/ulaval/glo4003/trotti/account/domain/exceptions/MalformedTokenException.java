package ca.ulaval.glo4003.trotti.account.domain.exceptions;

public class MalformedTokenException extends AuthenticationException {
    public MalformedTokenException(String message) {
        super(message);
    }
}
