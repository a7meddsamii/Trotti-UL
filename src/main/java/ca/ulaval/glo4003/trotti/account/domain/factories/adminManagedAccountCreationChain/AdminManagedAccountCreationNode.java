package ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
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

    public abstract Account createAdminManagedAccount(String name, LocalDate birthDate,
            Gender gender, Idul idul, Email email, Password password, Role role,
            Set<Permission> availablePermissions);

}
