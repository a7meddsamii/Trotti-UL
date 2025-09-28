package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.AccountRegistration;
import ca.ulaval.glo4003.trotti.domain.account.*;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApplicationServiceTest {

    private static final String MOCK_HASHED_PASSWORD = "mockHashedPassword123";

    private AccountRepository accountRepository;
    private AuthenticationService authenticationService;
    private AccountFactory accountFactory;
    private PasswordHasher passwordHasher;
    private AccountApplicationService accountApplicationService;

    private AccountRegistration accountRegistration;
    private Account mockAccount;

    @BeforeEach
    void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        passwordHasher = Mockito.mock(PasswordHasher.class);
        accountFactory = Mockito.mock(AccountFactory.class);

        Mockito.when(passwordHasher.hash(Mockito.anyString())).thenReturn(MOCK_HASHED_PASSWORD);

        accountApplicationService = new AccountApplicationService(accountRepository,
                authenticationService, accountFactory, passwordHasher);

        accountRegistration = createValidAccountRegistration();
        mockAccount = Mockito.mock(Account.class);
    }

    @Test
    void givenValidAccountRegistration_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createAccount(accountRegistration);

        Mockito.verify(accountRepository).save(mockAccount);
    }

    @Test
    void givenValidAccountRegistration_whenCreateAccount_thenAccountFactoryIsCalledWithCorrectParameters() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createAccount(accountRegistration);

        Mockito.verify(accountFactory).create(Mockito.eq(AccountFixture.A_NAME),
                Mockito.eq(AccountFixture.A_BIRTHDATE), Mockito.eq(AccountFixture.A_GENDER),
                Mockito.eq(AccountFixture.AN_IDUL), Mockito.eq(AccountFixture.AN_EMAIL),
                Mockito.any(Password.class));
    }

    @Test
    void givenValidAccountRegistration_whenCreateAccount_thenPasswordHasherHashIsCalled() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createAccount(accountRegistration);

        Mockito.verify(passwordHasher).hash(AccountFixture.A_RAW_PASSWORD);
    }

    @Test
    void givenValidAccountRegistration_whenCreateAccount_thenReturnCreatedIdul() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);

        Idul idul = accountApplicationService.createAccount(accountRegistration);

        Assertions.assertEquals(AccountFixture.AN_IDUL, idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(accountRegistration);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(mockAccount)));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(accountRegistration);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentEmail_whenLogin_thenThrowAuthenticationException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());

        Executable loginAttempt = () -> accountApplicationService
                .login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowAuthenticationException() {
        mockExistingAccountWithInvalidPassword();

        Executable loginAttempt = () -> accountApplicationService
                .login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnAuthenticationToken() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        AuthenticationToken resultToken = accountApplicationService
                .login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertEquals(AccountFixture.AN_AUTH_TOKEN, resultToken);
    }

    @Test
    void givenValidCredentials_whenLogin_thenAuthenticationServiceIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL_STRING,
                AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(authenticationService).generateToken(AccountFixture.AN_IDUL);
    }

    @Test
    void givenValidCredentials_whenLogin_thenFindByEmailIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL_STRING,
                AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(accountRepository).findByEmail(AccountFixture.AN_EMAIL);
    }

    @Test
    void givenValidCredentials_whenLogin_thenAccountVerifyPasswordMatchesIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL_STRING,
                AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(mockAccount).verifyPasswordMatches(AccountFixture.A_RAW_PASSWORD);
    }

    @Test
    void givenValidCredentials_whenLogin_thenNoAuthenticationExceptionIsThrown() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        Executable login = () -> accountApplicationService.login(AccountFixture.AN_EMAIL_STRING,
                AccountFixture.A_RAW_PASSWORD);

        Assertions.assertDoesNotThrow(login);
    }

    private AccountRegistration createValidAccountRegistration() {
        return new AccountRegistration(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }

    private void mockRepositoryToReturnNoExistingAccount() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn(Optional.empty());
    }

    private void mockFactoryToReturnValidAccount() {
        Mockito.when(accountFactory.create(Mockito.eq(AccountFixture.A_NAME),
                Mockito.eq(AccountFixture.A_BIRTHDATE), Mockito.eq(AccountFixture.A_GENDER),
                Mockito.eq(AccountFixture.AN_IDUL), Mockito.eq(AccountFixture.AN_EMAIL),
                Mockito.any(Password.class))).thenReturn(mockAccount);
    }

    private void mockExistingAccountWithInvalidPassword() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));
        Mockito.doThrow(new AuthenticationException("Invalid email or password")).when(mockAccount)
                .verifyPasswordMatches(AccountFixture.A_RAW_PASSWORD);
    }

    private void mockExistingAccountWithValidPassword() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);
    }
}
