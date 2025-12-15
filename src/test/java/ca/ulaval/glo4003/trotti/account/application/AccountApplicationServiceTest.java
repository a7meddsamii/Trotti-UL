package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.provider.PasswordAuthenticationProvider;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
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
    private RegistrationDto registrationDto;
    private Account account;
    private PasswordAuthenticationProvider authenticationProvider;
    private EventBus eventBus;

    private AccountApplicationService accountApplicationService;

    @BeforeEach
    void setup() {
        registrationDto = createValidPasswordRegistrationDto();
        account = new AccountFixture().build();

        accountRepository = Mockito.mock(AccountRepository.class);
        accountFactory = Mockito.mock(AccountFactory.class);
        authenticationProvider = Mockito.mock(PasswordAuthenticationProvider.class);
        sessionTokenProvider = Mockito.mock(SessionTokenProvider.class);
        eventBus = Mockito.mock(EventBus.class);

        accountApplicationService = new AccountApplicationService(accountRepository, accountFactory,
                sessionTokenProvider, authenticationProvider, eventBus);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockRepositoryToReturnNoExistingAccount();
        mockAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Mockito.verify(accountRepository).save(account);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenReturnIdul() {
        mockRepositoryToReturnNoExistingAccount();
        mockAuthenticationProviderToReturnAccountDto();

        Idul idul = accountApplicationService.createAccount(registrationDto);

        Assertions.assertEquals(registrationDto.idul(), idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(account)));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenRegisterIsCalled() {
        mockRepositoryToReturnNoExistingAccount();
        mockAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Mockito.verify(authenticationProvider).register(registrationDto);
    }

    @Test
    void givenNonExistentEmail_whenLogin_thenThrowAuthenticationException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        LoginDto loginDto = new LoginDto(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        Executable loginAttempt = () -> accountApplicationService.login(loginDto);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenExistingEmail_whenCreateAdminManagedAccount_thenThrowAlreadyExistsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, AccountFixture.AN_IDUL);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentCreatorAccount_whenCreateAdminManagedAccount_thenThrowAuthenticationException() {
        mockAuthenticationProviderToReturnAccountDto();
        mockRepositoryToReturnNoExistingAccount();
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn(Optional.empty());

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, AccountFixture.AN_IDUL);

        Assertions.assertThrows(AuthenticationException.class, accountCreationAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenGenerateTokenIsCalled() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));
        Mockito.when(authenticationProvider.verify(Mockito.any()))
                .thenReturn(AccountFixture.AN_EMAIL);

        LoginDto loginDto = new LoginDto(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);

        accountApplicationService.login(loginDto);

        Mockito.verify(sessionTokenProvider).generateToken(AccountFixture.AN_IDUL,
                AccountFixture.A_ROLE, AccountFixture.A_SET_OF_PERMISSION);
    }

    private RegistrationDto createValidPasswordRegistrationDto() {
        return new RegistrationDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
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
        Mockito.when(accountFactory.create(Mockito.eq(dto.name()), Mockito.eq(dto.birthDate()),
                Mockito.eq(dto.gender()), Mockito.eq(dto.idul()), Mockito.eq(dto.email()),
                Mockito.eq(dto.role()))).thenReturn(account);
    }

    private void mockAuthenticationProviderToReturnAccountDto() {
        AccountDto dto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);

        Mockito.when(authenticationProvider.register(registrationDto)).thenReturn(dto);

        mockFactoryToReturnValidAccount(dto);
    }
}
