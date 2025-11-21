package ca.ulaval.glo4003.trotti.billing.domain.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentIntent;

public interface PaymentGateway {
    boolean pay(PaymentIntent paymentIntent);
}
