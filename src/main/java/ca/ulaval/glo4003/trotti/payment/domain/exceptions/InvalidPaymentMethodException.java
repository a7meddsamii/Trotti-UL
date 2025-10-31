package ca.ulaval.glo4003.trotti.payment.domain.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {

    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}
