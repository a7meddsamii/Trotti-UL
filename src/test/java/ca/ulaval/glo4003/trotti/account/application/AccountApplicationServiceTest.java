package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApplicationServiceTest {

    private AccountRepository accountRepository;
    private AuthenticationService authenticationService;
    private AccountFactory accountFactory;
    private AccountDto accountDto;
    private Account mockAccount;

    private AccountApplicationService accountApplicationService;

    @BeforeEach
    void setup() {
        accountDto = createValidAccountDto();
        mockAccount = Mockito.mock(Account.class);
        accountRepository = Mockito.mock(AccountRepository.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        accountFactory = Mockito.mock(AccountFactory.class);
        accountApplicationService = new AccountApplicationService(accountRepository,
                authenticationService, accountFactory);
    }

    @Test
    void givenAccountDto_whenCreateAccount_thenAccountFactoryIsCalled() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createAccount(accountDto);

        Mockito.verify(accountFactory).create(accountDto.name(), accountDto.birthDate(),
                accountDto.gender(), accountDto.idul(), accountDto.email(), accountDto.password());
    }

    @Test
    void givenValidAccountDto_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createAccount(accountDto);

        Mockito.verify(accountRepository).save(mockAccount);
    }

    @Test
    void givenValidAccountDto_whenCreateAccount_thenReturnIdul() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);

        Idul idul = accountApplicationService.createAccount(accountDto);

        Assertions.assertEquals(accountDto.idul(), idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(accountDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(mockAccount)));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(accountDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentEmail_whenLogin_thenThrowAuthenticationException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());

        Executable loginAttempt = () -> accountApplicationService.login(AccountFixture.AN_EMAIL,
                AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowAuthenticationException() {
        mockExistingAccountWithInvalidPassword();

        Executable loginAttempt = () -> accountApplicationService.login(AccountFixture.AN_EMAIL,
                AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenAuthenticationServiceIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(authenticationService).generateToken(AccountFixture.AN_IDUL);
    }

    @Test
    void givenValidCredentials_whenLogin_thenFindByEmailIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(accountRepository).findByEmail(AccountFixture.AN_EMAIL);
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnAuthenticationToken() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        AuthenticationToken resultToken = accountApplicationService.login(AccountFixture.AN_EMAIL,
                AccountFixture.A_RAW_PASSWORD);

        Assertions.assertEquals(AccountFixture.AN_AUTH_TOKEN, resultToken);
    }

    @Test
    void givenCredentials_whenLogin_thenAccountVerifyPasswordIsCalled() {
        mockExistingAccountWithValidPassword();
        Mockito.when(authenticationService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);

        accountApplicationService.login(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(mockAccount).verifyPassword(AccountFixture.A_RAW_PASSWORD);
    }

    private AccountDto createValidAccountDto() {
        return new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_PASSWORD);
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
                .verifyPassword(AccountFixture.A_RAW_PASSWORD);
    }

    private void mockExistingAccountWithValidPassword() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));
        Mockito.when(mockAccount.verifyPassword(AccountFixture.A_RAW_PASSWORD)).thenReturn(true);
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);
    }
}
