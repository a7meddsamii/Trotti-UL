package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.StationResource;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripController;
import ca.ulaval.glo4003.trotti.trip.api.controllers.TripResource;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;

public class TripResourceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripResource();
        loadHeartbeatResource();
        loadStationResource();
    }

    private void loadStationResource() {
        StationController stationController = new StationController();

        this.resourceLocator.register(StationResource.class, stationController);
    }

    private void loadHeartbeatResource() {
        this.resourceLocator.register(HeartbeatResource.class, new HeartbeatController());
    }

    private void loadTripResource() {
        TripApiMapper tripApiMapper = resourceLocator.resolve(TripApiMapper.class);
        TripApplicationService tripApplicationService =
                resourceLocator.resolve(TripApplicationService.class);

        TripResource tripController = new TripController(tripApplicationService, tripApiMapper);
        resourceLocator.register(TripResource.class, tripController);
    }
}
