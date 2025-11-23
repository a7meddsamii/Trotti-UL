package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

import java.time.Clock;

public class RidePermitServiceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadRidePermitActivationFilter();
	}
	
	private void loadRidePermitActivationFilter() {
		Clock clock = this.resourceLocator.resolve(Clock.class);
		SchoolSessionProvider schoolSessionProvider = this.resourceLocator.resolve(SchoolSessionProvider.class);
		RidePermitActivationFilter ridePermitActivationFilter = new RidePermitActivationFilter(
				clock, schoolSessionProvider
		);
		this.resourceLocator.register(RidePermitActivationFilter.class, ridePermitActivationFilter);
	}
}
