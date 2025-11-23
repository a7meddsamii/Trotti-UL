package ca.ulaval.glo4003.trotti.billing.infrastructure.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.MissingPaymentMethodException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.TransactionId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethod;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Clock;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CreditCardPaymentGateway implements PaymentGateway {
    private static final Map<Idul, CreditCard> creditCards = new ConcurrentHashMap<>();

    private final Clock clock;

    public CreditCardPaymentGateway(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethod(Idul buyerId) {
        return Optional.ofNullable(creditCards.get(buyerId));
    }

    @Override
    public PaymentReceipt pay(PaymentIntent paymentIntent) {
        confirmAvailableCreditCard(paymentIntent.getBuyerId(), paymentIntent);
        CreditCard creditCard = (CreditCard) paymentIntent.getMethod();
        creditCards.putIfAbsent(paymentIntent.getBuyerId(), creditCard);

        boolean isTransactionSuccess = !YearMonth.now(Clock.systemDefaultZone()).isAfter(creditCard.getExpiryDate());

        String description = getDescription(isTransactionSuccess, creditCard.getCardNumber());

        return PaymentReceipt.of(TransactionId.randomId(), paymentIntent.getOrderId(),
                paymentIntent.getAmount(), isTransactionSuccess, description);
    }

    private void confirmAvailableCreditCard(Idul buyerId, PaymentIntent paymentIntent) {
        if (!paymentIntent.isForPaymentMethodType(PaymentMethodType.CREDIT_CARD)
                && !creditCards.containsKey(buyerId)) {
            throw new MissingPaymentMethodException("Credit card payment method is required");
        }
    }

    private String getDescription(boolean isTransactionSuccess, String cardNumber) {
        if (!isTransactionSuccess) {
            return String.format("Payment has failed with card ending with %s", cardNumber);
        }

        return String.format("Payment was proceeded successfully with card ending with %s", cardNumber);

    }
}
