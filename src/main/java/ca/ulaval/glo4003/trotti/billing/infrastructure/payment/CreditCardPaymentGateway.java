package ca.ulaval.glo4003.trotti.billing.infrastructure.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.MissingPaymentMethodException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.TransactionId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreditCardPaymentGateway implements PaymentGateway {
    private static final Map<Idul, CreditCard> creditCards = new ConcurrentHashMap<>();

    @Override
    public PaymentReceipt pay(PaymentIntent paymentIntent) {
        confirmAvailableCreditCard(paymentIntent.getBuyerId(), paymentIntent);
        CreditCard creditCard = (CreditCard) paymentIntent.getMethod();
        creditCards.putIfAbsent(paymentIntent.getBuyerId(), creditCard);

        return PaymentReceipt.of(TransactionId.randomId(), paymentIntent.getOrderId(),
                paymentIntent.getAmount(), true);
    }

    private void confirmAvailableCreditCard(Idul buyerId, PaymentIntent paymentIntent) {
        if (!paymentIntent.isForPaymentMethodType(PaymentMethodType.CREDIT_CARD)
                && !creditCards.containsKey(buyerId)) {
            throw new MissingPaymentMethodException("Credit card payment method is required");
        }
    }
}
