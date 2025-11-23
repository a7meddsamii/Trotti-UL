package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.DockingAndUndockingApplicationService;

public class TripGatewayEntryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadStationOperationEntry();
	}
	
	private void loadStationOperationEntry() {
		DockingAndUndockingApplicationService dockingAndUndockingApplicationService = this.resourceLocator.resolve(DockingAndUndockingApplicationService.class);
		StationApiMapper stationApiMapper = this.resourceLocator.resolve(StationApiMapper.class);
		StationOperationEntry stationOperationEntry = new StationOperationEntry(dockingAndUndockingApplicationService, stationApiMapper);
		this.resourceLocator.register(StationOperationEntry.class, stationOperationEntry);
	}
}
