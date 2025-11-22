package ca.ulaval.glo4003.trotti.billing.domain.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;

public interface PaymentGateway {
	PaymentReceipt pay(PaymentIntent paymentIntent);
}
