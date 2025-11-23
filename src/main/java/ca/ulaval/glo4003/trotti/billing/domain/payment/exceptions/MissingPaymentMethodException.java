package ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions;

public class MissingPaymentMethodException extends RuntimeException {

    public MissingPaymentMethodException(String message) {
        super(message);
    }
}
