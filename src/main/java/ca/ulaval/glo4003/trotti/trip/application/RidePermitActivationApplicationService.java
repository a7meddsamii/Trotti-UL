package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.application.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import java.util.List;

public class RidePermitActivationApplicationService {

    private final TravelerRepository travelerRepository;
    private final RidePermitHistoryGateway ridePermitHistoryGateway;
    private final NotificationService<List<RidePermit>> ridePermitNotificationService;
    private final EmployeeRidePermitService employeeRidePermitService;
    private final RidePermitMapper ridePermitMapper;

    public RidePermitActivationApplicationService(
            TravelerRepository travelerRepository,
            RidePermitHistoryGateway ridePermitHistoryGateway,
            NotificationService<List<RidePermit>> ridePermitNotificationService,
            EmployeeRidePermitService employeeRidePermitService,
            RidePermitMapper ridePermitMapper) {
        this.travelerRepository = travelerRepository;
        this.ridePermitHistoryGateway = ridePermitHistoryGateway;
        this.ridePermitNotificationService = ridePermitNotificationService;
        this.employeeRidePermitService = employeeRidePermitService;
        this.ridePermitMapper = ridePermitMapper;
    }

    public void updateActivatedRidePermits() {
        List<Traveler> travelers = travelerRepository.findAll();
        travelers.forEach(this::processTraveler);
    }

    private void processTraveler(Traveler traveler) {
        List<RidePermit> boughtRidePermitsHistory =
                ridePermitHistoryGateway.getFullHistory(traveler.getIdul());
        List<RidePermit> newlyActivatedRidePermits;

        if (employeeRidePermitService.isEmployee(traveler.getIdul())) {
            newlyActivatedRidePermits =
                    employeeRidePermitService.giveFreePermitToEmployee(traveler);
        } else {
            newlyActivatedRidePermits = traveler.updateWallet(boughtRidePermitsHistory);
        }

        travelerRepository.update(traveler);

        if (!newlyActivatedRidePermits.isEmpty()) {
            ridePermitNotificationService.notify(traveler.getEmail(), newlyActivatedRidePermits);
        }
    }
}
