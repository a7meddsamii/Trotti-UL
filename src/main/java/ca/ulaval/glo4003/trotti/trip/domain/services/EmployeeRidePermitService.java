package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeRidePermitService {
    private final EmployeeRegistryProvider employeeRegistryProvider;
    private final SchoolSessionProvider schoolSessionProvider;

    public EmployeeRidePermitService(
            EmployeeRegistryProvider employeeRegistryProvider,
            SchoolSessionProvider schoolSessionProvider) {
        this.employeeRegistryProvider = employeeRegistryProvider;
        this.schoolSessionProvider = schoolSessionProvider;
    }

    public List<RidePermit> giveFreePermitToEmployee(Traveler traveler) {
        LocalDate currentDate = LocalDate.now();
        Optional<Session> session = schoolSessionProvider.getSession(currentDate);

        if (!traveler.hasEmptyWallet() || session.isEmpty()) {
            return Collections.emptyList();
        }

        RidePermit employeeRidePermit =
                new RidePermit(RidePermitId.randomId(), traveler.getIdul(), session.get());

        return traveler.updateWallet(List.of(employeeRidePermit));
    }

    @Deprecated
    public boolean isEmployee(Idul idul) {
        return employeeRegistryProvider.exists(idul);
    }
}
