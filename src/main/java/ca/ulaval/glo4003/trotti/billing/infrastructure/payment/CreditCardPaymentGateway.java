package ca.ulaval.glo4003.trotti.billing.infrastructure.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CreditCardPaymentGateway implements PaymentGateway {
	private static final Map<Idul, CreditCard> creditCards =  new ConcurrentHashMap<>();

    @Override
	public PaymentReceipt pay(PaymentIntent paymentIntent) {
		
		CreditCard creditCard = (CreditCard) paymentIntent.getMethod();
		creditCards.put(paymentIntent.getBuyerId(), creditCard);
		
        return PaymentReceipt.of(paymentIntent.getOrderId(), paymentIntent.getAmount());
    }
}
