package ca.ulaval.glo4003.trotti.domain.order.exceptions;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
