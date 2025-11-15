package ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.AccountPermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.MaintenancePermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.TripPermissions;
import java.time.LocalDate;
import java.util.Set;

public class TechnicianCreationNode extends CompanyAccountCreationNode {

    private final Set<Permission> permissions = Set.of(TripPermissions.MAKE_TRIP,
            MaintenancePermissions.START_MAINTENANCE, MaintenancePermissions.END_MAINTENANCE,
            MaintenancePermissions.RELOCATE_SCOOTER, MaintenancePermissions.REQUEST_MAINTENANCE);

    public TechnicianCreationNode(CompanyAccountCreationNode next) {
        this.next = next;
    }

    @Override
    public Account CreateCompanyAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role, Set<Permission> availablePermissions) {

        if (role == Role.TECHNICIAN) {

            if (!availablePermissions.contains(AccountPermissions.CREATE_TECHNICIAN)) {
                throw new AuthorizationException("Not permitted");
            }

            return new Account(name, birthDate, gender, idul, email, password, role, permissions);
        }
        return next.CreateCompanyAccount(name, birthDate, gender, idul, email, password, role,
                availablePermissions);
    }
}
