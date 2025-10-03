package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class RidePermitActivationApplicationService {

    private final TravelerRepository travelerRepository;
    private final RidePermitHistoryGateway ridePermitHistoryGateway;
    private final NotificationService<List<RidePermit>> ridePermitNotificationService;
	private final EmployeeRidePermitService employeeRidePermitService;

    public RidePermitActivationApplicationService(
			TravelerRepository travelerRepository,
			RidePermitHistoryGateway ridePermitHistoryGateway,
			NotificationService<List<RidePermit>> ridePermitNotificationService,
			EmployeeRidePermitService employeeRidePermitService) {
        this.travelerRepository = travelerRepository;
        this.ridePermitHistoryGateway = ridePermitHistoryGateway;
        this.ridePermitNotificationService = ridePermitNotificationService;
		this.employeeRidePermitService = employeeRidePermitService;
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
		employeeRidePermitService.handleEmployeeRidePermit(traveler);
        travelerRepository.update(traveler);

        if (!newlyActivatedRidePermits.isEmpty()) {
            ridePermitNotificationService.notify(traveler.getEmail(), newlyActivatedRidePermits);
        }
    }
}
