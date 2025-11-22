package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class RidePermitActivationService {
	private final Clock clock;
	private final SchoolSessionProvider schoolSessionProvider;
	
	
	public RidePermitActivationService(Clock clock, SchoolSessionProvider schoolSessionProvider) {
		this.clock = clock;
		this.schoolSessionProvider = schoolSessionProvider;
	}
	
	public List<RidePermit> getActivatedRidePermits(List<RidePermit> ridePermits){
		LocalDate now = clock.instant().atZone(clock.getZone()).toLocalDate();
		Session currentSchoolSession = schoolSessionProvider.getSession(now).orElseThrow(()->new NotFoundException("Could not find an active school session"));
		return ridePermits.stream()
				.filter(ridePermit -> ridePermit.activate(currentSchoolSession))
				.toList();
	}
}
