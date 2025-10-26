package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.application.trip.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.EmployeeRidePermitService;
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

    public List<RidePermitDto> getRidePermits(Idul idul) {
        Traveler traveler = travelerRepository.findByIdul(idul);

        return ridePermitMapper.toDto(traveler.getWalletPermits());
    }
}
