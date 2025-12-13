package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.application.FleetApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.TemporaryFleetApplicationService;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;

import java.time.Clock;

public class FleetApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadFleetApplicationService();
    }
	
	/**
	 * @deprecated replace TemporaryFleetApplicationService by FleetApplicationService when the implementation is ready
	 */
    private void loadFleetApplicationService(){
		FleetRepository fleetRepository = this.resourceLocator.resolve(FleetRepository.class);
		Clock clock = this.resourceLocator.resolve(Clock.class);
		FleetApplicationService fleetApplicationService = new TemporaryFleetApplicationService(fleetRepository, clock);
		this.resourceLocator.register(FleetApplicationService.class, fleetApplicationService);
	}
}
