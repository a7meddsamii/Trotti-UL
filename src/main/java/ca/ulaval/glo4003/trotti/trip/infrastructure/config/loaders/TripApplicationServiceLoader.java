package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.application.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.trip.domain.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import java.time.Clock;
import java.util.List;

public class TripApplicationServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadRidePermitActivationApplicationService();
        loadUnlockCodeApplicationService();
        loadTripApplicationService();
    }

    private void loadRidePermitActivationApplicationService() {
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        RidePermitHistoryGateway ridePermitHistoryGateway =
                this.resourceLocator.resolve(RidePermitHistoryGateway.class);
        NotificationService<List<RidePermit>> notificationService =
                this.resourceLocator.resolve(RidePermitNotificationService.class);
        EmployeeRidePermitService employeeRidePermitService =
                this.resourceLocator.resolve(EmployeeRidePermitService.class);

        RidePermitActivationApplicationService ridePermitActivationService =
                new RidePermitActivationApplicationService(travelerRepository,
                        ridePermitHistoryGateway, notificationService, employeeRidePermitService);
        this.resourceLocator.register(RidePermitActivationApplicationService.class,
                ridePermitActivationService);
    }

    private void loadUnlockCodeApplicationService() {
        UnlockCodeService unlockCodeService = this.resourceLocator.resolve(UnlockCodeService.class);
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        NotificationService<UnlockCode> notificationService =
                this.resourceLocator.resolve(UnlockCodeNotificationService.class);

        UnlockCodeApplicationService unlockCodeApplicationService =
                new UnlockCodeApplicationService(unlockCodeService, travelerRepository,
                        notificationService);

        this.resourceLocator.register(UnlockCodeApplicationService.class,
                unlockCodeApplicationService);
    }

    private void loadTripApplicationService() {
        TravelerRepository travelerRepository =
                this.resourceLocator.resolve(TravelerRepository.class);
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);
        TripRepository tripRepository = this.resourceLocator.resolve(TripRepository.class);
        UnlockCodeService unlockCodeService = this.resourceLocator.resolve(UnlockCodeService.class);
        Clock clock = this.resourceLocator.resolve(Clock.class);
        TripApplicationService tripApplicationService =
                new TripApplicationService(travelerRepository, stationRepository, scooterRepository,
                        tripRepository, unlockCodeService, clock);
        this.resourceLocator.register(TripApplicationService.class, tripApplicationService);
    }
}
