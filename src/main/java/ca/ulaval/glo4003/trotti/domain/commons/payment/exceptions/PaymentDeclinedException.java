package ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions;

public class PaymentDeclinedException extends PaymentException {

    public PaymentDeclinedException(String message) {
        super("PAYMENT_DECLINED: " + message);
    }
}
