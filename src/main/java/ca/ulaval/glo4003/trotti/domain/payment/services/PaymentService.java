package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentRequestException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;

public class PaymentService {

    public void process(CreditCard paymentMethod, Money amountToPay) {
        if (paymentMethod == null) {
            throw new InvalidPaymentRequestException("Payment method cannot be null");
        }

        if (amountToPay == null || amountToPay.isNegative()) {
            throw new InvalidPaymentRequestException("Amount to pay must not be null nor negative");
        }

        if (paymentMethod.isExpired()) {
            throw new PaymentDeclinedException("Payment method is expired.");
        }

        paymentMethod.pay(amountToPay);
    }
}
