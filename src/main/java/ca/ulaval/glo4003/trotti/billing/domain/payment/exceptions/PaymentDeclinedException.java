package ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions;

public class PaymentDeclinedException extends PaymentException {

    public PaymentDeclinedException(String message) {
        super("PAYMENT_DECLINED: " + message);
    }
}
