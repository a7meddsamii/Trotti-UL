package ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}
