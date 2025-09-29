package ca.ulaval.glo4003.trotti.domain.payment.exceptions;

public class PaymentDeclinedException extends Exception {
    public PaymentDeclinedException(String message) {
        super("PAYMENT_DECLINED: " + message);
    }
}
