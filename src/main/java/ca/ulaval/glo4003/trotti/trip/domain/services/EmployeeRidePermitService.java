package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.gateways.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.commons.domain.gateways.SchoolSessionGateway;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeRidePermitService {
    private final EmployeeRegistryGateway employeeRegistryGateway;
    private final SchoolSessionGateway schoolSessionGateway;

    public EmployeeRidePermitService(
            EmployeeRegistryGateway employeeRegistryGateway,
            SchoolSessionGateway schoolSessionGateway) {
        this.employeeRegistryGateway = employeeRegistryGateway;
        this.schoolSessionGateway = schoolSessionGateway;
    }

    public List<RidePermit> giveFreePermitToEmployee(Traveler traveler) {
        LocalDate currentDate = LocalDate.now();
        Optional<Session> session = schoolSessionGateway.getSession(currentDate);

        if (!traveler.hasEmptyWallet() || session.isEmpty()) {
            return Collections.emptyList();
        }

        RidePermit employeeRidePermit =
                new RidePermit(RidePermitId.randomId(), traveler.getIdul(), session.get());

        return traveler.updateWallet(List.of(employeeRidePermit));
    }

    public boolean isEmployee(Idul idul) {
        return employeeRegistryGateway.exist(idul);
    }
}
