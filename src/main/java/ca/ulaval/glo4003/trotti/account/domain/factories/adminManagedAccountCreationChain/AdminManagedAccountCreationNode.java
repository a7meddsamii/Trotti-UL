package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.UnableToCreateAccountException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import java.time.LocalDate;
import java.util.Set;

public abstract class AdminManagedAccountCreationNode {

    protected AdminManagedAccountCreationNode next;

    public AdminManagedAccountCreationNode setNext(AdminManagedAccountCreationNode next) {
        this.next = next;
        return this;
    }

    public Account createAdminManagedAccount(String name, LocalDate birthDate, Gender gender,
                                             Idul idul, Email email, Password password, Role role, Set<Permission> creatorPermissions) {
        if (responsibilityRole().equals(role)) {
            return createAccount(name, birthDate, gender, idul, email, password, role, creatorPermissions);
        } else if (next != null) {
            return next.createAdminManagedAccount(name, birthDate, gender, idul, email, password, role, creatorPermissions);
        } else {
            throw new UnableToCreateAccountException("unable to create account");
        }
    }

    protected abstract Account createAccount(String name, LocalDate birthDate, Gender gender,
                                             Idul idul, Email email, Password password, Role role, Set<Permission> creatorPermissions);

    protected abstract Role responsibilityRole();
}
