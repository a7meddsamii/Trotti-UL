package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.*;

public class TripResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripResource();
        loadHeartbeatResource();
        loadStationResource();
    }

    private void loadStationResource() {
        TransferApplicationService transferApplicationService =
                this.resourceLocator.resolve(TransferApplicationService.class);
        StationMaintenanceApplicationService stationMaintenanceApplicationService =
                this.resourceLocator.resolve(StationMaintenanceApplicationService.class);
        StationApiMapper stationApiMapper = this.resourceLocator.resolve(StationApiMapper.class);
        DockingAndUndockingApplicationService dockingAndUndockingApplicationService =
                this.resourceLocator.resolve(DockingAndUndockingApplicationService.class);
        StationController stationController = new StationController(transferApplicationService,
                stationMaintenanceApplicationService, stationApiMapper,
                dockingAndUndockingApplicationService);

        this.resourceLocator.register(StationResource.class, stationController);
    }

    private void loadHeartbeatResource() {
        this.resourceLocator.register(HeartbeatResource.class, new HeartbeatController());
    }

    private void loadTripResource() {
        TripApiMapper tripApiMapper = resourceLocator.resolve(TripApiMapper.class);
        TripCommandApplicationService tripCommandApplicationService =
                resourceLocator.resolve(TripCommandApplicationService.class);
        TripQueryApplicationService tripQueryApplicationService =
                resourceLocator.resolve(TripQueryApplicationService.class);

        TripResource tripController = new TripController(tripCommandApplicationService, tripQueryApplicationService, tripApiMapper);
        resourceLocator.register(TripResource.class, tripController);
    }
}
