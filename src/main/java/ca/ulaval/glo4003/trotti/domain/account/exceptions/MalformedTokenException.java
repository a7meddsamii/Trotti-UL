package ca.ulaval.glo4003.trotti.domain.account.exceptions;

public class MalformedTokenException extends AuthenticationException {
    public MalformedTokenException(String message) {
        super(message);
    }
}
