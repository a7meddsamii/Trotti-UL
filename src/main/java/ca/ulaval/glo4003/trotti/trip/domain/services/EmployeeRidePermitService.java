package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.commons.domain.values.SessionEnum;
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
    private final SessionEnum sessionEnum;

    public EmployeeRidePermitService(EmployeeRegistry employeeRegistry, SessionEnum sessionEnum) {
        this.employeeRegistry = employeeRegistry;
        this.sessionEnum = sessionEnum;
    }

    public List<RidePermit> giveFreePermitToEmployee(Traveler traveler) {
        LocalDate currentDate = LocalDate.now();
        Optional<Session> session = sessionEnum.getSession(currentDate);

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
