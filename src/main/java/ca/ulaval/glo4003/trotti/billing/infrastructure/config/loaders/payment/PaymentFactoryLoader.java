package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.payment;

import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class PaymentFactoryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		DataCodec dataCodec = this.resourceLocator.resolve(DataCodec.class);
		PaymentMethodFactory paymentMethodFactory = new PaymentMethodFactory(
				dataCodec
		);
		this.resourceLocator.register(PaymentMethodFactory.class, paymentMethodFactory);
	}
}
