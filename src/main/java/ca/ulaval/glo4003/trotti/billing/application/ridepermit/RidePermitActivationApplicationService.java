package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.ActivatedRidePermitEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;

import java.time.LocalDate;
import java.util.List;

public class RidePermitActivationApplicationService {
	private final RidePermitAssembler ridePermitAssembler;
	private final RidePermitRepository ridePermitRepository;
	private final RidePermitActivationFilter ridePermitActivationFilter;
	private final EventBus eventBus;
	
	public RidePermitActivationApplicationService(RidePermitAssembler ridePermitAssembler, RidePermitRepository ridePermitRepository, RidePermitActivationFilter ridePermitActivationFilter, EventBus eventBus) {
		this.ridePermitAssembler = ridePermitAssembler;
		this.ridePermitRepository = ridePermitRepository;
		this.ridePermitActivationFilter = ridePermitActivationFilter;
		this.eventBus = eventBus;
	}
	
	public void activateRidePermit() {
		LocalDate currentSessionDate  = ridePermitActivationFilter.getCurrentSessionDate();
		List<RidePermit> foundRidePermits = ridePermitRepository.findAllByDate(currentSessionDate);
		List<RidePermit> activatedRidePermit = ridePermitActivationFilter.getActivatedRidePermits(foundRidePermits);
		ridePermitRepository.saveAll(activatedRidePermit);
		
		List< RidePermitSnapshot> ridePermitSnapshots = ridePermitAssembler.toRidePermitSnapshots(activatedRidePermit);
		eventBus.publish(new ActivatedRidePermitEvent(ridePermitSnapshots));
	}
	
	public void deactivateRidePermit() {
		Session previousSession  = ridePermitActivationFilter.getPreviousSession();
		List<RidePermit> foundRidePermits = ridePermitRepository.findAllBySession(previousSession);
		List<RidePermit> deactivatedRidePermit = ridePermitActivationFilter.getDeactivatedRidePermits(foundRidePermits);
		ridePermitRepository.saveAll(deactivatedRidePermit);
	}
}
