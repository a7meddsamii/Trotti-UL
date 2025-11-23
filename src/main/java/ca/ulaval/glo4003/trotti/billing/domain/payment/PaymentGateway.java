package ca.ulaval.glo4003.trotti.billing.domain.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethod;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Optional;

public interface PaymentGateway {

    Optional<PaymentMethod> getPaymentMethod(Idul buyerId);

    PaymentReceipt pay(PaymentIntent paymentIntent);
}
