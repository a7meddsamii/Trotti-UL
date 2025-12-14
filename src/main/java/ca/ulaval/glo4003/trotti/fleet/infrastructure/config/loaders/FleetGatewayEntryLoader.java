package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.api.gatewayentry.StationOperationEntry;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.ScooterRentalApplicationService;

public class FleetGatewayEntryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadStationOperationEntry();
    }

    private void loadStationOperationEntry() {
        ScooterRentalApplicationService scooterRentalApplicationService =
                this.resourceLocator.resolve(ScooterRentalApplicationService.class);
        FleetApiMapper fleetApiMapper = this.resourceLocator.resolve(FleetApiMapper.class);
        StationOperationEntry stationOperationEntry =
                new StationOperationEntry(fleetApiMapper, scooterRentalApplicationService);
        this.resourceLocator.register(StationOperationEntry.class, stationOperationEntry);
    }
}
