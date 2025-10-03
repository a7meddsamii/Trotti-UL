package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeRidePermitService {
	private final EmployeeRegistry employeeRegistry;
	private final SessionRegistry sessionRegistry;
	
	public EmployeeRidePermitService(EmployeeRegistry employeeRegistry, SessionRegistry sessionRegistry) {
		this.employeeRegistry = employeeRegistry;
		this.sessionRegistry = sessionRegistry;
	}
	
	public void handleEmployeeRidePermit(Traveler traveler){
		LocalDate currentDate = LocalDate.now();
		Optional<Session> session = sessionRegistry.getSession(currentDate);
				
		if (employeeRegistry.isEmployee(traveler.getIdul()) && !traveler.hasActiveRidePermits() && session.isPresent()){
			RidePermit employeeRidePermit = new RidePermit(Id.randomId(), traveler.getIdul(), session.get());
			traveler.updateActiveRidePermits(List.of(employeeRidePermit));
		}
	}
}