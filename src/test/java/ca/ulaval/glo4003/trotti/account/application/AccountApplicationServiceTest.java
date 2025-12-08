package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountValidator;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.ApplyAdvantageRequestEvent;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApplicationServiceTest {

    private static final RegistrationDto VALID_REGISTRATION_DTO = new RegistrationDto(
            AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE, AccountFixture.A_GENDER,
            AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD,
            AccountFixture.A_ROLE);

    private AccountRepository accountRepository;
    private AuthenticationProvider authenticationProvider;
    private SessionTokenProvider sessionTokenProvider;
    private EventBus eventBus;

    private AccountApplicationService accountApplicationService;

    @BeforeEach
    void setup() {
        Map<Idul, AccountRecord> accountTable = new HashMap<>();
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(accountTable, accountMapper);

        AccountValidator accountValidator = Mockito.mock(AccountValidator.class);
        StandardAccountCreationNode standardChain = Mockito.mock(StandardAccountCreationNode.class);
        AdminManagedAccountCreationNode adminChain = Mockito.mock(AdminManagedAccountCreationNode.class);
        AccountFactory accountFactory = new AccountFactory(accountValidator, standardChain, adminChain);

        authenticationProvider = Mockito.mock(AuthenticationProvider.class);
        sessionTokenProvider = Mockito.mock(SessionTokenProvider.class);
        eventBus = Mockito.mock(EventBus.class);

        accountApplicationService = new AccountApplicationService(accountRepository, accountFactory,
                sessionTokenProvider, authenticationProvider, eventBus);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);
        accountRepository.save(new AccountFixture().build());

        // when
        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(VALID_REGISTRATION_DTO);

        // then
        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);
        accountRepository.save(new AccountFixture().build());

        // when
        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(VALID_REGISTRATION_DTO);

        // then
        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentEmail_whenLogin_thenThrowAuthenticationException() {
        // given
        LoginDto loginDto = new LoginDto(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);
        Mockito.when(authenticationProvider.verify(loginDto)).thenReturn(AccountFixture.AN_EMAIL);

        // when
        Executable loginAttempt = () -> accountApplicationService.login(loginDto);

        // then
        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenExistingEmail_whenCreateAdminManagedAccount_thenThrowAlreadyExistsException() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);
        accountRepository.save(new AccountFixture().build());

        // when
        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(VALID_REGISTRATION_DTO, AccountFixture.AN_IDUL);

        // then
        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentCreatorAccount_whenCreateAdminManagedAccount_thenThrowAuthenticationException() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);

        // when
        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(VALID_REGISTRATION_DTO, AccountFixture.AN_IDUL);

        // then
        Assertions.assertThrows(AuthenticationException.class, accountCreationAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenGenerateTokenIsCalled() {
        // given
        accountRepository.save(new AccountFixture().build());
        LoginDto loginDto = new LoginDto(AccountFixture.AN_EMAIL, AccountFixture.A_RAW_PASSWORD);
        Mockito.when(authenticationProvider.verify(loginDto)).thenReturn(AccountFixture.AN_EMAIL);

        // when
        accountApplicationService.login(loginDto);

        // then
        Mockito.verify(sessionTokenProvider).generateToken(AccountFixture.AN_IDUL,
                AccountFixture.A_ROLE, AccountFixture.A_SET_OF_PERMISSION);
    }

    @Test
    void givenValidRegistration_whenCreateAccount_thenAccountIsSaved() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);

        // when
        accountApplicationService.createAccount(VALID_REGISTRATION_DTO);

        // then
        Assertions.assertTrue(accountRepository.findByIdul(AccountFixture.AN_IDUL).isPresent());
    }

    @Test
    void givenValidRegistration_whenCreateAccount_thenReturnIdul() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);

        // when
        Idul result = accountApplicationService.createAccount(VALID_REGISTRATION_DTO);

        // then
        Assertions.assertEquals(AccountFixture.AN_IDUL, result);
    }

    @Test
    void givenValidRegistration_whenCreateAccount_thenPublishAccountCreatedEvent() {
        // given
        AccountDto accountDto = new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_ROLE);
        Mockito.when(authenticationProvider.register(VALID_REGISTRATION_DTO)).thenReturn(accountDto);

        // when
        accountApplicationService.createAccount(VALID_REGISTRATION_DTO);

        // then
        Mockito.verify(eventBus).publish(Mockito.any());
    }

    @Test
    void givenAccountsWithAdvantage_whenRenewAdvantage_thenPublishApplyAdvantageRequestEvent() {
        // given
        accountRepository.save(new AccountFixture().build());
        Advantage advantage = AccountFixture.A_SET_OF_ADVANTAGES.iterator().next();

        // when
        accountApplicationService.renewAdvantage(advantage);

        // then
        Mockito.verify(eventBus).publish(Mockito.any(ApplyAdvantageRequestEvent.class));
    }

    @Test
    void givenNoAccountsWithAdvantage_whenRenewAdvantage_thenDoNotPublishEvent() {
        // when
        accountApplicationService.renewAdvantage(AccountFixture.A_SET_OF_ADVANTAGES.iterator().next());

        // then
        Mockito.verify(eventBus, Mockito.never()).publish(Mockito.any());
    }
}
