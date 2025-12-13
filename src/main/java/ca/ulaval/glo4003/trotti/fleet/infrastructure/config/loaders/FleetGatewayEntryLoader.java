package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.fleet.application.FleetApplicationService;
import ca.ulaval.glo4003.trotti.trip.api.mappers.FleetApiMapper;

public class FleetGatewayEntryLoader extends Bootstrapper {

    @Override
    public void load() {
		loadStationOperationEntry();
    }
	
	private void loadStationOperationEntry() {
		FleetApplicationService fleetApplicationService = this.resourceLocator.resolve(FleetApplicationService.class);
		FleetApiMapper fleetApiMapper = this.resourceLocator.resolve(FleetApiMapper.class);
		StationOperationEntry stationOperationEntry = new StationOperationEntry(fleetApiMapper, fleetApplicationService);
		this.resourceLocator.register(StationOperationEntry.class, stationOperationEntry);
	}
}
