package ca.ulaval.glo4003.trotti.billing.domain.order.exceptions;

public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }
}
