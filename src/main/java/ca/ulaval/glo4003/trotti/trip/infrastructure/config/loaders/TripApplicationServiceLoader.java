package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;

import java.time.Clock;

public class TripApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripApplicationService();
    }


    private void loadTripApplicationService() {
        RidePermitGateway ridePermitGateway = this.resourceLocator.resolve(RidePermitGateway.class);
        ScooterRentalGateway scooterRentalGateway =
                this.resourceLocator.resolve(ScooterRentalGateway.class);
        UnlockCodeStore unlockCodeStore = this.resourceLocator.resolve(UnlockCodeStore.class);
        TripRepository tripRepository = this.resourceLocator.resolve(TripRepository.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        TripApplicationService tripApplicationService = new TripApplicationService(unlockCodeStore,
                tripRepository, ridePermitGateway, scooterRentalGateway, eventBus, clock);
        this.resourceLocator.register(TripApplicationService.class, tripApplicationService);
    }
}
