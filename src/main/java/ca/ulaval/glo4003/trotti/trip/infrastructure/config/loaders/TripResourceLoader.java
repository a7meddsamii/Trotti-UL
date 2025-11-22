package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.UnlockCodeController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.UnlockCodeResource;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.StationMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TransferApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;

public class TripResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadUnlockCodeResource();
        loadTripResource();
        loadHeartbeatResource();
        loadStationResource();
    }

    private void loadStationResource() {
        AuthenticationService authenticationService =
                this.resourceLocator.resolve(AuthenticationService.class);
        TransferApplicationService transferApplicationService =
                this.resourceLocator.resolve(TransferApplicationService.class);
        StationMaintenanceApplicationService stationMaintenanceApplicationService =
                this.resourceLocator.resolve(StationMaintenanceApplicationService.class);
        StationApiMapper stationApiMapper = this.resourceLocator.resolve(StationApiMapper.class);
        StationController stationController = new StationController(transferApplicationService,
                stationMaintenanceApplicationService, authenticationService, stationApiMapper);

        this.resourceLocator.register(StationResource.class, stationController);
    }

    private void loadHeartbeatResource() {
        this.resourceLocator.register(HeartbeatResource.class, new HeartbeatController());
    }

    private void loadUnlockCodeResource() {
        UnlockCodeApplicationService unlockCodeApplicationService =
                this.resourceLocator.resolve(UnlockCodeApplicationService.class);
        UnlockCodeResource unlockCodeController =
                new UnlockCodeController(unlockCodeApplicationService);
        this.resourceLocator.register(UnlockCodeResource.class, unlockCodeController);
    }

    private void loadTripResource() {
        TripApiMapper tripApiMapper = resourceLocator.resolve(TripApiMapper.class);
        TripApplicationService tripApplicationService =
                resourceLocator.resolve(TripApplicationService.class);

        TripResource tripController = new TripController(tripApplicationService, tripApiMapper);
        resourceLocator.register(TripResource.class, tripController);
    }
}
