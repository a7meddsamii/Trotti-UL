package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public class AccountFactory {

    private final AccountValidator accountValidator;
    private final StandardAccountCreationNode userChain;
    private final AdminManagedAccountCreationNode companyChain;

    public AccountFactory(
            AccountValidator accountValidator,
            StandardAccountCreationNode userChain,
            AdminManagedAccountCreationNode companyChain) {
        this.accountValidator = accountValidator;
        this.userChain = userChain;
        this.companyChain = companyChain;
    }

    public Account create(String name, LocalDate birthDate, Gender gender, Idul idul, Email email, Role role) {

        accountValidator.validateBirthDate(birthDate);
        return userChain.createStandardAccount(name, birthDate, gender, idul, email, role);
    }

    public Account create(String name, LocalDate birthDate, Gender gender, Idul idul, Email email, Role role, Set<Permission> creatorPermissions) {

        accountValidator.validateBirthDate(birthDate);
        return companyChain.createAdminManagedAccount(name, birthDate, gender, idul, email, role, creatorPermissions);
    }
}
