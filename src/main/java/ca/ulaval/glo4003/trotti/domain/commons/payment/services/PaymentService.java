package ca.ulaval.glo4003.trotti.domain.commons.payment.services;

import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.InvalidPaymentRequestException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.PaymentException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.Transaction;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.TransactionStatus;

public class PaymentService {

    private static final String CVV_REGEX = "^\\d{3,4}$";

    public Transaction process(CreditCard paymentMethod, Money amountToPay, String cvv) {
        try {
            processPayment(paymentMethod, amountToPay, cvv);
            return new Transaction(TransactionStatus.SUCCESS, amountToPay,
                    "Payment processed successfully with card ending in "
                            + paymentMethod.getCardNumber());
        } catch (PaymentException e) {
            return new Transaction(TransactionStatus.FAILED, amountToPay, e.getMessage());
        }
    }

    private void processPayment(CreditCard paymentMethod, Money amountToPay, String cvv) {
        if (paymentMethod == null) {
            throw new InvalidPaymentRequestException("No payment method associated with buyer.");
        }

        if (amountToPay == null || amountToPay.isNegative()) {
            throw new InvalidPaymentRequestException("Amount to pay must not be null nor negative");
        }

        if (cvv == null || !cvv.matches(CVV_REGEX)) {
            throw new InvalidPaymentRequestException("CVV is invalid.");
        }

        if (paymentMethod.isExpired()) {
            throw new PaymentDeclinedException("Payment method is expired.");
        }

        paymentMethod.pay(amountToPay);
    }
}
