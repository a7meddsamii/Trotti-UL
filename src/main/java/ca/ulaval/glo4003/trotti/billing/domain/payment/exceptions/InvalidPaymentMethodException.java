package ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {

    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}
