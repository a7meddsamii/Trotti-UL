package ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.AccountPermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;

import java.time.LocalDate;
import java.util.Set;

public class AdminCreationNode extends CompanyAccountCreationNode {

    private final Set<Permission> permissions = Set.of(AccountPermissions.values());

    public AdminCreationNode(CompanyAccountCreationNode next) {
        this.next = next;
    }

    @Override
    public Account CreateCompanyAccount(String name, LocalDate birthDate, Gender gender, Idul idul, Email email, Password password, Role role, Set<Permission> availablePermissions) {

        if (role == Role.ADMIN) {

            if(! availablePermissions.contains(AccountPermissions.CREATE_ADMIN) ) {
                throw new AuthorizationException("Not permitted");
            }

            return new Account(name,birthDate,gender,idul,email,password,role, permissions);
        }
        return next.CreateCompanyAccount(name, birthDate, gender, idul, email, password, role, availablePermissions);
    }
}
