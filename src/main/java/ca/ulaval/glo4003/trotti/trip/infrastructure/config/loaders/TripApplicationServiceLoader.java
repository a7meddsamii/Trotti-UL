package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.application.TripCommandApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripQueryApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.mappers.TripMapper;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripCommandRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripQueryRepository;
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
        TripCommandRepository tripCommandRepository = this.resourceLocator.resolve(TripCommandRepository.class);
        TripQueryRepository tripQueryRepository = this.resourceLocator.resolve(TripQueryRepository.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        TripMapper tripMapper = this.resourceLocator.resolve(TripMapper.class);
        TripCommandApplicationService tripCommandApplicationService =
                new TripCommandApplicationService(unlockCodeStore, tripCommandRepository, ridePermitGateway,
                        scooterRentalGateway, eventBus, clock);
        TripQueryApplicationService tripQueryApplicationService =
                new TripQueryApplicationService(tripQueryRepository);
        this.resourceLocator.register(TripCommandApplicationService.class, tripCommandApplicationService);
        this.resourceLocator.register(TripQueryApplicationService.class, tripQueryApplicationService);
    }
}
