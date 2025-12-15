package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.api.FleetController;
import ca.ulaval.glo4003.trotti.fleet.api.FleetResource;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.FleetMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.FleetOperationsApplicationService;

public class FleetResourceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadFleetStationResource();
    }

    private void loadFleetStationResource() {
        FleetMaintenanceApplicationService maintenanceService =
                resourceLocator.resolve(FleetMaintenanceApplicationService.class);
        FleetOperationsApplicationService operationsService =
                resourceLocator.resolve(FleetOperationsApplicationService.class);
        FleetApiMapper fleetApiMapper = resourceLocator.resolve(FleetApiMapper.class);

        FleetResource fleetController =
                new FleetController(maintenanceService, operationsService, fleetApiMapper);

        resourceLocator.register(FleetResource.class, fleetController);
    }
}
