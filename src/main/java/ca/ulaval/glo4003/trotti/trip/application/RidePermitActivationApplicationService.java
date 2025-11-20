package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import java.util.List;

public class RidePermitActivationApplicationService {

    private final TravelerRepository travelerRepository;
    private final RidePermitGateway ridePermitGateway;
    private final NotificationService<List<RidePermit>> ridePermitNotificationService;
    private final EmployeeRidePermitService employeeRidePermitService;

    public RidePermitActivationApplicationService(
            TravelerRepository travelerRepository,
            RidePermitGateway ridePermitGateway,
            NotificationService<List<RidePermit>> ridePermitNotificationService,
            EmployeeRidePermitService employeeRidePermitService) {
        this.travelerRepository = travelerRepository;
        this.ridePermitGateway = ridePermitGateway;
        this.ridePermitNotificationService = ridePermitNotificationService;
        this.employeeRidePermitService = employeeRidePermitService;
    }

    public void updateActivatedRidePermits() {
        List<Traveler> travelers = travelerRepository.findAll();
        travelers.forEach(this::processTraveler);
    }

    private void processTraveler(Traveler traveler) {
        List<RidePermit> boughtRidePermitsHistory =
                ridePermitGateway.findAllByIdul(traveler.getIdul());
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
