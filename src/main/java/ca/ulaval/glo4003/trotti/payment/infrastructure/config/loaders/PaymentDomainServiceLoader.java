package ca.ulaval.glo4003.trotti.payment.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;

public class PaymentDomainServiceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		this.resourceLocator.register(PaymentService.class, new PaymentService());
	}
}
