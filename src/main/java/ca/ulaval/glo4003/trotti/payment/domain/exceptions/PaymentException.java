package ca.ulaval.glo4003.trotti.payment.domain.exceptions;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}
