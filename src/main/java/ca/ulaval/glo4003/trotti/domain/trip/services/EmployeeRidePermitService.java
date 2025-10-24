package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeRidePermitService {
    private final EmployeeRegistry employeeRegistry;
    private final SessionRegistry sessionRegistry;

    public EmployeeRidePermitService(
            EmployeeRegistry employeeRegistry,
            SessionRegistry sessionRegistry) {
        this.employeeRegistry = employeeRegistry;
        this.sessionRegistry = sessionRegistry;
    }

    public List<RidePermit> handleEmployeeRidePermit(Traveler traveler) {
        LocalDate currentDate = LocalDate.now();
        Optional<Session> session = sessionRegistry.getSession(currentDate);

        if (traveler.hasActiveRidePermits() || session.isEmpty()) {
            return Collections.emptyList();
        }

        RidePermit employeeRidePermit =
                new RidePermit(RidePermitId.randomId(), traveler.getIdul(), session.get());

        return traveler.updateActiveRidePermits(List.of(employeeRidePermit));
    }

    public boolean isEmployee(Idul idul) {
        return employeeRegistry.isEmployee(idul);
    }
}
