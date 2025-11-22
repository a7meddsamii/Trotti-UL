package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public class TechnicianCreationNode extends AdminManagedAccountCreationNode {

    private final Set<Permission> permissions =
            Set.of(Permission.MAKE_TRIP, Permission.START_MAINTENANCE, Permission.END_MAINTENANCE,
                    Permission.RELOCATE_SCOOTERS, Permission.REQUEST_MAINTENANCE);

    @Override
    protected Role responsibilityRole() {
        return Role.TECHNICIAN;
    }

    @Override
    protected Account createAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role, Set<Permission> creatorPermissions) {

        if (!creatorPermissions.contains(Permission.CREATE_EMPLOYEE)) {
            throw new AuthorizationException("Not permitted");
        }

        return new Account(name, birthDate, gender, idul, email, password, role, permissions);
    }
}
