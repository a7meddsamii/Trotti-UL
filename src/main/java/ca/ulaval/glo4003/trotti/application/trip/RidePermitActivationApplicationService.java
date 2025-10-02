package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import java.util.List;

public class RidePermitActivationApplicationService {

    private final TravelerRepository travelerRepository;
    private final RidePermitHistoryGateway ridePermitHistoryGateway;
    private final NotificationService<List<RidePermit>> ridePermitNotificationService;

    public RidePermitActivationApplicationService(
            TravelerRepository travelerRepository,
            RidePermitHistoryGateway ridePermitHistoryGateway,
            RidePermitNotificationService ridePermitNotificationService) {
        this.travelerRepository = travelerRepository;
        this.ridePermitHistoryGateway = ridePermitHistoryGateway;
        this.ridePermitNotificationService = ridePermitNotificationService;
    }

    public void updateActivatedRidePermits() {
        List<Traveler> travelers = travelerRepository.findAll();
        travelers.forEach(this::processTraveler);
    }

    private void processTraveler(Traveler traveler) {
        List<RidePermit> boughtRidePermitsHistory =
                ridePermitHistoryGateway.getFullHistory(traveler.getIdul());
        List<RidePermit> newlyActivatedRidePermits =
                traveler.updateActiveRidePermits(boughtRidePermitsHistory);
        travelerRepository.update(traveler);

        if (!newlyActivatedRidePermits.isEmpty()) {
            ridePermitNotificationService.notify(traveler.getEmail(), newlyActivatedRidePermits);
        }
    }
}
