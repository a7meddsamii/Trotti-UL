package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository.InMemoryRidePermitRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitRepositoryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadOrderRepository();
	}
	
	private void loadOrderRepository() {
		RidePermitRepository ridePermitRepository = new InMemoryRidePermitRepository();
		this.resourceLocator.register(RidePermitRepository.class, ridePermitRepository);
	}
}
