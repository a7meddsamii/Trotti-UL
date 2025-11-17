package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.MaintenancePermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.TripPermissions;
import java.time.LocalDate;
import java.util.Set;

public class EmployeeCreationNode extends StandardAccountCreationNode {

    private final Set<Permission> permissions =
            Set.of(TripPermissions.MAKE_TRIP, MaintenancePermissions.REQUEST_MAINTENANCE);

    @Override
    protected Role responsibilityRole() {
        return Role.EMPLOYEE;
    }

    @Override
    protected Account createAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role) {
        return new Account(name, birthDate, gender, idul, email, password, role, permissions);
    }
}
