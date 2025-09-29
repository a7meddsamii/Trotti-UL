package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;

public class PaymentService {

    public void process(PaymentMethod paymentMethod, Money amountToPay) {
        if (paymentMethod == null) {
            throw new InvalidParameterException("Payment method cannot be null");
        }

        if (amountToPay == null || amountToPay.isNegative()) {
            throw new InvalidParameterException("Amount to pay must not be null nor negative");
        }

        if (paymentMethod.isExpired()) {
            throw new PaymentDeclinedException("Payment method is expired.");
        }

        paymentMethod.pay(amountToPay);
    }
}
