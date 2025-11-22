package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public class AdminCreationNode extends AdminManagedAccountCreationNode {

    private final Set<Permission> permissions = Set.of(Permission.DELETE_EMPLOYEE,
            Permission.CREATE_EMPLOYEE, Permission.CREATE_ADMIN, Permission.DELETE_ADMIN);

    @Override
    protected Role responsibilityRole() {
        return Role.ADMIN;
    }

    @Override
    protected Account createAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Role role, Set<Permission> creatorPermissions) {

        if (!creatorPermissions.contains(Permission.CREATE_ADMIN)) {
            throw new AuthorizationException("Not permitted");
        }

        return new Account(name, birthDate, gender, idul, email, role, permissions);
    }
}
