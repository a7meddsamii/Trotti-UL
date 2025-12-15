package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountValidator;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.TechnicianCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.EmployeeCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StudentCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountApplicationServiceIntegrationTest {

    private AccountRepository accountRepository;
    private AccountFactory accountFactory;
    private SessionTokenProvider sessionTokenProvider;
    private RegistrationDto registrationDto;
    private AuthenticationProvider authenticationProvider;
    private EventBus eventBus;
    private EmployeeRegistryProvider employeeRegistryProvider;

    private AccountApplicationService accountApplicationService;

    @BeforeEach
    void setup() {
        registrationDto = createValidPasswordRegistrationDto();

        Map<Idul, AccountRecord> accountTable = new HashMap<>();
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(accountTable, accountMapper);

        eventBus = Mockito.mock(EventBus.class);
        accountFactory = createRealAccountFactory();

        employeeRegistryProvider = Mockito.mock(EmployeeRegistryProvider.class);
        authenticationProvider = Mockito.mock(AuthenticationProvider.class);
        sessionTokenProvider = Mockito.mock(SessionTokenProvider.class);

        accountApplicationService = new AccountApplicationService(accountRepository, accountFactory,
                sessionTokenProvider, authenticationProvider, eventBus);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Optional<Account> savedAccount = accountRepository.findByIdul(AccountFixture.IDUL);
        Assertions.assertTrue(savedAccount.isPresent());
        Assertions.assertEquals(AccountFixture.IDUL, savedAccount.get().getIdul());
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenReturnIdul() {
        mockAuthenticationProviderToReturnAccountDto();

        Idul idul = accountApplicationService.createAccount(registrationDto);

        Assertions.assertEquals(registrationDto.idul(), idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Account existingAccount = new AccountFixture().build();
        accountRepository.save(existingAccount);

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Account existingAccount = new AccountFixture().build();
        accountRepository.save(existingAccount);

        Executable accountCreationAttempt =
                () -> accountApplicationService.createAccount(registrationDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenValidPasswordRegistrationDto_whenCreateAccount_thenAuthenticationProviderRegisterIsCalled() {
        mockAuthenticationProviderToReturnAccountDto();

        accountApplicationService.createAccount(registrationDto);

        Mockito.verify(authenticationProvider).register(registrationDto);
    }

    @Test
    void givenNonExistentEmail_whenLogin_thenThrowsException() {
        Email nonExistentEmail = Email.from("nonexistent@ulaval.ca");
        LoginDto loginDto = new LoginDto(nonExistentEmail, AccountFixture.RAW_PASSWORD);
        Mockito.when(authenticationProvider.verify(loginDto)).thenReturn(nonExistentEmail);

        Executable loginAttempt = () -> accountApplicationService.login(loginDto);

        Assertions.assertThrows(AuthenticationException.class, loginAttempt);
    }

    @Test
    void givenExistingEmail_whenCreateAdminManagedAccount_thenThrowsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Account existingAccount = new AccountFixture().build();
        accountRepository.save(existingAccount);

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, AccountFixture.IDUL);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenNonExistentCreatorAccount_whenCreateAdminManagedAccount_thenThrowsException() {
        mockAuthenticationProviderToReturnAccountDto();
        Idul nonExistentCreatorIdul = Idul.from("NOEXIST");

        Executable accountCreationAttempt = () -> accountApplicationService
                .createAdminManagedAccount(registrationDto, nonExistentCreatorIdul);

        Assertions.assertThrows(AuthenticationException.class, accountCreationAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenGeneratesToken() {
        Account existingAccount = new AccountFixture().build();
        accountRepository.save(existingAccount);
        LoginDto loginDto = new LoginDto(AccountFixture.EMAIL, AccountFixture.RAW_PASSWORD);
        Mockito.when(authenticationProvider.verify(loginDto)).thenReturn(AccountFixture.EMAIL);
        SessionToken expectedToken = SessionToken.from("test-token");
        Mockito.when(sessionTokenProvider.generateToken(AccountFixture.IDUL, AccountFixture.ROLE,
                AccountFixture.SET_OF_PERMISSION)).thenReturn(expectedToken);

        SessionToken actualToken = accountApplicationService.login(loginDto);

        Mockito.verify(sessionTokenProvider).generateToken(AccountFixture.IDUL, AccountFixture.ROLE,
                AccountFixture.SET_OF_PERMISSION);
        Assertions.assertEquals(expectedToken, actualToken);
    }

    private RegistrationDto createValidPasswordRegistrationDto() {
        return new RegistrationDto(AccountFixture.NAME, AccountFixture.BIRTHDATE,
                AccountFixture.GENDER, AccountFixture.IDUL, AccountFixture.EMAIL,
                AccountFixture.RAW_PASSWORD, AccountFixture.ROLE);
    }

    private void mockAuthenticationProviderToReturnAccountDto() {
        AccountDto dto =
                new AccountDto(AccountFixture.NAME, AccountFixture.BIRTHDATE, AccountFixture.GENDER,
                        AccountFixture.IDUL, AccountFixture.EMAIL, AccountFixture.ROLE);
        Mockito.when(authenticationProvider.register(registrationDto)).thenReturn(dto);
    }

    private AccountFactory createRealAccountFactory() {
        AccountValidator accountValidator = new AccountValidator(Clock.systemDefaultZone());
        StandardAccountCreationNode standardChain = buildStandardAccountChain();
        AdminManagedAccountCreationNode adminManagedChain = buildAdminManagedAccountChain();
        return new AccountFactory(accountValidator, standardChain, adminManagedChain);
    }

    private StandardAccountCreationNode buildStandardAccountChain() {
        StandardAccountCreationNode employee = new EmployeeCreationNode(employeeRegistryProvider);
        StandardAccountCreationNode student = new StudentCreationNode();
        employee.setNext(student);
        return employee;
    }

    private AdminManagedAccountCreationNode buildAdminManagedAccountChain() {
        AdminManagedAccountCreationNode admin = new AdminCreationNode();
        AdminManagedAccountCreationNode technician = new TechnicianCreationNode();
        admin.setNext(technician);
        return admin;
    }
}
