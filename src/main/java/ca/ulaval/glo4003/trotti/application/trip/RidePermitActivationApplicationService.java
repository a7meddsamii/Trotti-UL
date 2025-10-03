package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.application.trip.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
			EmployeeRidePermitService employeeRidePermitService, RidePermitMapper ridePermitMapper
	) {
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
        List<RidePermit> newlyActivatedRidePermits =
                traveler.updateActiveRidePermits(boughtRidePermitsHistory);
		employeeRidePermitService.handleEmployeeRidePermit(traveler);
        travelerRepository.update(traveler);

        if (!newlyActivatedRidePermits.isEmpty()) {
            ridePermitNotificationService.notify(traveler.getEmail(), newlyActivatedRidePermits);
        }
    }
	
	public List<RidePermitDto> getRidePermit(Idul idul) {
		Optional<Traveler> traveler = travelerRepository.findAll().stream().filter(t -> t.getIdul().equals(idul)).findFirst();
		
		if (traveler.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ridePermitMapper.toDto(traveler.get().getRidePermits());
	}
}
