package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.StationMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TransferApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.mappers.TripMapper;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import java.time.Clock;

public class TripApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripApplicationService();
        loadStationMaintenanceApplicationService();
        loadTransferApplicationService();
    }

    private void loadTransferApplicationService() {
        TransferRepository transferRepository =
                this.resourceLocator.resolve(TransferRepository.class);
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);

        TransferApplicationService transferApplicationService = new TransferApplicationService(
                transferRepository, stationRepository, scooterRepository, clock);
        this.resourceLocator.register(TransferApplicationService.class, transferApplicationService);
    }

    private void loadStationMaintenanceApplicationService() {
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);

        StationMaintenanceApplicationService stationMaintenanceApplicationService =
                new StationMaintenanceApplicationService(stationRepository, scooterRepository,
                        eventBus, clock);
        this.resourceLocator.register(StationMaintenanceApplicationService.class,
                stationMaintenanceApplicationService);
    }

    private void loadTripApplicationService() {
        RidePermitGateway ridePermitGateway = this.resourceLocator.resolve(RidePermitGateway.class);
        ScooterRentalGateway scooterRentalGateway =
                this.resourceLocator.resolve(ScooterRentalGateway.class);
        UnlockCodeStore unlockCodeStore = this.resourceLocator.resolve(UnlockCodeStore.class);
        TripRepository tripRepository = this.resourceLocator.resolve(TripRepository.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        TripMapper tripMapper = this.resourceLocator.resolve(TripMapper.class);
        TripApplicationService tripApplicationService = new TripApplicationService(unlockCodeStore,
                tripRepository, ridePermitGateway, scooterRentalGateway, eventBus, clock, tripMapper);
        this.resourceLocator.register(TripApplicationService.class, tripApplicationService);
    }
}
