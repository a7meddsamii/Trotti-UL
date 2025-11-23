package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitRepositoryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadOrderRepository();
	}
	
	private void loadOrderRepository() {
		// TODO : Wait for merge for implementation of RidePermitRepository
		// RidePermitRepository ridePermitRepository = new RidePermitRepository();
		// this.resourceLocator.register(RidePermitRepository.class, ridePermitRepository);
	}
}
