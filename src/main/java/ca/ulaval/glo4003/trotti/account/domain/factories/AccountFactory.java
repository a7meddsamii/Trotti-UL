package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.CompanyAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.UserAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;

import java.time.LocalDate;
import java.util.Set;

public class AccountFactory {


    private final BirthdayValidation birthdayValidation;
    private final UserAccountCreationNode userChain;
    private final CompanyAccountCreationNode companyChain;

    public AccountFactory(BirthdayValidation birthdayValidation, UserAccountCreationNode userChain, CompanyAccountCreationNode companyChain ) {
        this.birthdayValidation = birthdayValidation;
        this.userChain = userChain;
        this.companyChain = companyChain;
    }
    
    public Account create(String name, LocalDate birthDate, Gender gender,
                          Idul idul, Email email, Password password, Role role) {

        birthdayValidation.validateBirthDate(birthDate);
        return userChain.CreateUserAccount(name, birthDate, gender, idul, email, password, role);
    }

    public Account create(String name, LocalDate birthDate, Gender gender,
                           Idul idul, Email email, Password password, Role role, Set<Permission> availablePermissions) {

        birthdayValidation.validateBirthDate(birthDate);
        return companyChain.CreateCompanyAccount(name, birthDate, gender, idul, email, password, role, availablePermissions);
    }
}