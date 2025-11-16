package ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain;

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

    public EmployeeCreationNode(StandardAccountCreationNode next) {
        this.next = next;
    }

    @Override
    public Account createStandardAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role) {
        if (role == Role.EMPLOYEE) {
            return new Account(name, birthDate, gender, idul, email, password, role, permissions);
        }
        return next.createStandardAccount(name, birthDate, gender, idul, email, password, role);
    }
}
