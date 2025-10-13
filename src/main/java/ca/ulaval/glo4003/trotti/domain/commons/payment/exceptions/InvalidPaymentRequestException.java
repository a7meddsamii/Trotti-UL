package ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions;

public class InvalidPaymentRequestException extends PaymentException {

    public InvalidPaymentRequestException(String message) {
        super("INVALID_PAYMENT_REQUEST: " + message);
    }
}
