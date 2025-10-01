package ca.ulaval.glo4003.trotti.domain.payment.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {

    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}
