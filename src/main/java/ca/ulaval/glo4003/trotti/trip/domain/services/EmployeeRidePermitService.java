package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
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

    public List<RidePermit> giveFreePermitToEmployee(Traveler traveler) {
        LocalDate currentDate = LocalDate.now();
        Optional<Session> session = sessionRegistry.getSession(currentDate);

        if (!traveler.hasEmptyWallet() || session.isEmpty()) {
            return Collections.emptyList();
        }

        RidePermit employeeRidePermit =
                new RidePermit(RidePermitId.randomId(), traveler.getIdul(), session.get());

        return traveler.updateWallet(List.of(employeeRidePermit));
    }

    public boolean isEmployee(Idul idul) {
        return employeeRegistry.isEmployee(idul);
    }
}
