package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentRequestException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import ca.ulaval.glo4003.trotti.domain.payment.values.TransactionStatus;

public class PaymentService {

    public Transaction process(PaymentMethod paymentMethod, Money amountToPay) {
        try {
            processPayment(paymentMethod, amountToPay);
            return new Transaction(TransactionStatus.SUCCESS, amountToPay, "Payment processed successfully");
        }
        catch (PaymentException e) {
            return new Transaction(TransactionStatus.FAILED, amountToPay, e.getMessage());
        }
    }

    private void processPayment(PaymentMethod paymentMethod, Money amountToPay) {
        if (paymentMethod == null) {
            throw new InvalidPaymentRequestException("No payment method associated with buyer.");
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
