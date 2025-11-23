package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.DockingAndUndockingApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;

import java.time.Clock;

public class TripGatewayEntryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadGatewayApplicationServices();
        loadStationOperationEntry();
	}
	
	private void loadStationOperationEntry() {
		DockingAndUndockingApplicationService dockingAndUndockingApplicationService = this.resourceLocator.resolve(DockingAndUndockingApplicationService.class);
		StationApiMapper stationApiMapper = this.resourceLocator.resolve(StationApiMapper.class);
		StationOperationEntry stationOperationEntry = new StationOperationEntry(dockingAndUndockingApplicationService, stationApiMapper);
		this.resourceLocator.register(StationOperationEntry.class, stationOperationEntry);
	}

    private void loadGatewayApplicationServices() {
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        DockingAndUndockingApplicationService dockingAndUndockingApplicationService = new DockingAndUndockingApplicationService(
                stationRepository, scooterRepository, clock
        );
        this.resourceLocator.register(DockingAndUndockingApplicationService.class, dockingAndUndockingApplicationService);
    }
}
