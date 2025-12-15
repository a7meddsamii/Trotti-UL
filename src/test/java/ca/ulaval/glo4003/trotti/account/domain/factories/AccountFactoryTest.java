package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountFactoryTest {

    private static final String ACCOUNT_NAME = "John Doe";
    private static final LocalDate ACCOUNT_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender ACCOUNT_GENDER = Gender.MALE;
    private static final Idul ACCOUNT_IDUL = Idul.from("jdoe123");
    private static final Email ACCOUNT_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final Role ACCOUNT_ROLE = Role.STUDENT;
    private static final Set<Permission> CREATOR_PERMISSIONS = Set.of();

    private AccountValidator accountValidator;
    private StandardAccountCreationNode standardChain;
    private AdminManagedAccountCreationNode adminChain;
    private AccountFactory accountFactory;
    private Account expectedAccount;

    @BeforeEach
    void setup() {
        accountValidator = Mockito.mock(AccountValidator.class);
        standardChain = Mockito.mock(StandardAccountCreationNode.class);
        adminChain = Mockito.mock(AdminManagedAccountCreationNode.class);
        expectedAccount = Mockito.mock(Account.class);

        accountFactory = new AccountFactory(accountValidator, standardChain, adminChain);
    }

    @Test
    void givenValidParameters_whenCreateStandardAccount_thenValidatesBirthdateAndCallsStandardChain() {
        Mockito.when(standardChain.createStandardAccount(ACCOUNT_NAME, ACCOUNT_BIRTHDATE,
                ACCOUNT_GENDER, ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE))
                .thenReturn(expectedAccount);

        Account result = accountFactory.create(ACCOUNT_NAME, ACCOUNT_BIRTHDATE, ACCOUNT_GENDER,
                ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE);

        Mockito.verify(accountValidator).validateBirthDate(ACCOUNT_BIRTHDATE);
        Mockito.verify(standardChain).createStandardAccount(ACCOUNT_NAME, ACCOUNT_BIRTHDATE,
                ACCOUNT_GENDER, ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE);
        Assertions.assertEquals(expectedAccount, result);
    }

    @Test
    void givenValidParametersWithPermissions_whenCreateAdminManagedAccount_thenValidatesBirthdateAndCallsAdminChain() {
        Mockito.when(adminChain.createAdminManagedAccount(ACCOUNT_NAME, ACCOUNT_BIRTHDATE,
                ACCOUNT_GENDER, ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE, CREATOR_PERMISSIONS))
                .thenReturn(expectedAccount);

        Account result = accountFactory.create(ACCOUNT_NAME, ACCOUNT_BIRTHDATE, ACCOUNT_GENDER,
                ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE, CREATOR_PERMISSIONS);

        Mockito.verify(accountValidator).validateBirthDate(ACCOUNT_BIRTHDATE);
        Mockito.verify(adminChain).createAdminManagedAccount(ACCOUNT_NAME, ACCOUNT_BIRTHDATE,
                ACCOUNT_GENDER, ACCOUNT_IDUL, ACCOUNT_EMAIL, ACCOUNT_ROLE, CREATOR_PERMISSIONS);
        Assertions.assertEquals(expectedAccount, result);
    }
}
