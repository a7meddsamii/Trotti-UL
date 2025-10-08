package ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {

    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}
