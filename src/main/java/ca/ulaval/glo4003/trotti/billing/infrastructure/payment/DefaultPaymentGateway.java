package ca.ulaval.glo4003.trotti.billing.infrastructure.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentIntent;

public class DefaultPaymentGateway implements PaymentGateway {

    @Override
    public boolean pay(PaymentIntent paymentIntent) {
        return false;
    }
}
