package ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions;

public class InvalidPaymentRequestException extends PaymentException {

    public InvalidPaymentRequestException(String message) {
        super("INVALID_PAYMENT_REQUEST: " + message);
    }
}
