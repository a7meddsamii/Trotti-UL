package ca.ulaval.glo4003.trotti.billing.domain.order.exceptions;

public class SessionException extends RuntimeException {
    public SessionException(String message) {
        super(message);
    }
}
