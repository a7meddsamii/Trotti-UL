package ca.ulaval.glo4003.trotti.order.domain.exceptions;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
