package ca.ulaval.glo4003.trotti.domain.order.exceptions;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
