package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.CreditCardPaymentGateway;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.Clock;

public class PaymentGatewayLoader extends Bootstrapper {

    @Override
    public void load() {
        loadPaymentGatewayLoader();
    }

    private void loadPaymentGatewayLoader() {
        Clock clock = this.resourceLocator.resolve(Clock.class);

        PaymentGateway paymentGateway = new CreditCardPaymentGateway(clock);
        this.resourceLocator.register(PaymentGateway.class, paymentGateway);
    }
}
