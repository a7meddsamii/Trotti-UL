package ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.UnableToCreateAccountException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import java.time.LocalDate;
import java.util.Set;

public class NoAdminManagedAccountCreationNode extends AdminManagedAccountCreationNode {

    @Override
    public Account createAdminManagedAccount(String name, LocalDate birthDate, Gender gender,
            Idul idul, Email email, Password password, Role role,
            Set<Permission> availablePermissions) {
        throw new UnableToCreateAccountException("Unable to create company account");
    }
}
