package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.PasswordRegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.provider.PasswordAuthenticationProvider;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApplicationServiceTest {

    private AccountRepository accountRepository;
    private AccountFactory accountFactory;
    private SessionTokenProvider sessionTokenProvider;
    private PasswordRegistrationDto registrationDto;
    private AccountDto accountDto;
    private Account mockAccount;
    private PasswordAuthenticationProvider authenticationProvider;

    private AccountApplicationService accountApplicationService;

    @BeforeEach
    void setup() {
        registrationDto = createValidPasswordRegistrationDto();
        mockAccount = Mockito.mock(Account.class);

        accountRepository = Mockito.mock(AccountRepository.class);
        accountFactory = Mockito.mock(AccountFactory.class);
        authenticationProvider = Mockito.mock(PasswordAuthenticationProvider.class);
        sessionTokenProvider = Mockito.mock(SessionTokenProvider.class);

        accountApplicationService = new AccountApplicationService(
            accountRepository,
            accountFactory,
            sessionTokenProvider,
            authenticationProvider
        );
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockRepositoryToReturnNoExistingAccount();
        mockPasswordAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Mockito.verify(accountRepository).save(mockAccount);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenReturnIdul() {
        mockRepositoryToReturnNoExistingAccount();
        mockPasswordAuthenticationProviderToReturnAccountDto();
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);

        Idul idul = accountApplicationService.createAccount(registrationDto);

        Assertions.assertEquals(registrationDto.idul(), idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(mockAccount)));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenRegisterIsCalled() {
        mockRepositoryToReturnNoExistingAccount();
        mockPasswordAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Mockito.verify(authenticationProvider).register(registrationDto);
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
    void givenExistingEmail_whenCreateAdminManagedAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, AccountFixture.AN_IDUL);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentCreatorAccount_whenCreateAdminManagedAccount_thenThrowAuthenticationException() {
        mockRepositoryToReturnNoExistingAccount();
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn(Optional.empty());

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, AccountFixture.AN_IDUL);

        Assertions.assertThrows(AuthenticationException.class, accountCreationAttempt);
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowAuthenticationException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
            .thenReturn(Optional.of(mockAccount));
        Mockito.when(authenticationProvider.verify(Mockito.any()))
            .thenReturn(false);

        Executable loginAttempt =
            () -> accountApplicationService.login(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenGenerateTokenIsCalled() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
            .thenReturn(Optional.of(mockAccount));
        Mockito.when(authenticationProvider.verify(Mockito.any()))
            .thenReturn(true);

        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);
        Mockito.when(mockAccount.getRole()).thenReturn(AccountFixture.A_ROLE);
        Mockito.when(mockAccount.getPermissions()).thenReturn(AccountFixture.A_SET_OF_PERMISSION);

        accountApplicationService.login(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(sessionTokenProvider)
            .generateToken(AccountFixture.AN_IDUL, AccountFixture.A_ROLE, AccountFixture.A_SET_OF_PERMISSION);
    }

    private PasswordRegistrationDto createValidPasswordRegistrationDto() {
        return new PasswordRegistrationDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_RAW_PASSWORD, AccountFixture.A_ROLE);
    }

    private void mockRepositoryToReturnNoExistingAccount() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn(Optional.empty());
    }

    private void mockFactoryToReturnValidAccount(AccountDto dto) {
        Mockito.when(accountFactory.create(
            Mockito.eq(dto.name()),
            Mockito.eq(dto.birthDate()),
            Mockito.eq(dto.gender()),
            Mockito.eq(dto.idul()),
            Mockito.eq(dto.email()),
            Mockito.eq(dto.role())
        )).thenReturn(mockAccount);
    }

    private void mockPasswordAuthenticationProviderToReturnAccountDto() {
        AccountDto dto = new AccountDto(
            AccountFixture.A_NAME,
            AccountFixture.A_BIRTHDATE,
            AccountFixture.A_GENDER,
            AccountFixture.AN_IDUL,
            AccountFixture.AN_EMAIL,
            AccountFixture.A_ROLE
        );

        Mockito.when(authenticationProvider.register(registrationDto))
            .thenReturn(dto);

        mockFactoryToReturnValidAccount(dto);
    }


}
