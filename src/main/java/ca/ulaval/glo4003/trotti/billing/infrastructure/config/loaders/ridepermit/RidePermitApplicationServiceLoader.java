package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitAssembler;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

import java.time.Clock;

public class RidePermitApplicationServiceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadRidePermitActivationApplicationService();
		loadRidePermitApplicationService();
	}
	
	private void loadRidePermitActivationApplicationService() {
		RidePermitAssembler ridePermitAssembler = this.resourceLocator.resolve(RidePermitAssembler.class);
		RidePermitRepository ridePermitRepository = this.resourceLocator.resolve(RidePermitRepository.class);
		RidePermitActivationFilter ridePermitActivationFilter = this.resourceLocator.resolve(RidePermitActivationFilter.class);
		EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
		RidePermitActivationApplicationService ridePermitActivationApplicationService = new RidePermitActivationApplicationService(
				ridePermitAssembler, ridePermitRepository, ridePermitActivationFilter, eventBus
		);
		this.resourceLocator.register(RidePermitActivationApplicationService.class, ridePermitActivationApplicationService);
	}
	
	private void loadRidePermitApplicationService() {
		RidePermitFactory ridePermitFactory = this.resourceLocator.resolve(RidePermitFactory.class);
		RidePermitRepository ridePermitRepository = this.resourceLocator.resolve(RidePermitRepository.class);
		PaymentGateway paymentGateway = this.resourceLocator.resolve(PaymentGateway.class);
		RidePermitAssembler ridePermitAssembler = this.resourceLocator.resolve(RidePermitAssembler.class);
		Clock clock = this.resourceLocator.resolve(Clock.class);
		RidePermitApplicationService ridePermitApplicationService = new RidePermitApplicationService(
				ridePermitFactory, ridePermitRepository, paymentGateway, ridePermitAssembler, clock);
		this.resourceLocator.register(RidePermitApplicationService.class, ridePermitApplicationService);
	}
}
