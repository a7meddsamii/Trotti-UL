package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.trip.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import java.util.List;

public class RidePermitActivationApplicationService {

    private final TravelerRepository travelerRepository;
    private final RidePermitHistoryGateway ridePermitHistoryGateway;
    private final NotificationService notificationService;

    public RidePermitActivationApplicationService(
            TravelerRepository travelerRepository,
            RidePermitHistoryGateway ridePermitHistoryGateway,
            NotificationService notificationService) {
        this.travelerRepository = travelerRepository;
        this.ridePermitHistoryGateway = ridePermitHistoryGateway;
        this.notificationService = notificationService;
    }

    public void update() {
        List<Traveler> travelers = travelerRepository.findAll();
        travelers.forEach(this::processTraveler);
    }

    private void processTraveler(Traveler traveler) {
        try {
            List<RidePermit> boughtRidePermitsHistory =
                    ridePermitHistoryGateway.getFullHistory(traveler.getIdul());
            List<RidePermit> newlyActivatedRidePermits =
                    traveler.updateActiveRidePermits(boughtRidePermitsHistory);
            travelerRepository.update(traveler);
            notificationService.notify(traveler.getEmail(), newlyActivatedRidePermits);
        } catch (RuntimeException ignored) {
        }
    }
}
