package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.CreditCardPaymentGateway;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class PaymentGatewayLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadPaymentGatewayLoader();
	}
	
	private void loadPaymentGatewayLoader() {
		PaymentGateway paymentGateway = new CreditCardPaymentGateway();
		this.resourceLocator.register(PaymentGateway.class, paymentGateway);
	}
}
