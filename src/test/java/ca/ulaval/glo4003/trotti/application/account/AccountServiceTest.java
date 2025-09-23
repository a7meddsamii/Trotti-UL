package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.shared.exception.ConflictException;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class AccountServiceTest {

    private AccountRepository repository;
    private AccountMapper mapper;
    private AuthenticationService authService;
    private AccountService service;

    private CreateAccount request;
    private Account account;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(AccountRepository.class);
        mapper = Mockito.mock(AccountMapper.class);
        authService = Mockito.mock(AuthenticationService.class);
        service = new AccountService(repository, mapper, authService);

        account = Mockito.mock(Account.class);
        request = aCreateAccountRequest();
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenMapperCreateIsCalled() {
        Mockito.when(repository.existsByEmail(AccountFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountFixture.AN_IDUL)).thenReturn(false);
        Mockito.when(mapper.create(request)).thenReturn(account);

        service.createAccount(request);

        Mockito.verify(mapper).create(request);
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenRepositorySaveIsCalled() {
        Mockito.when(repository.existsByEmail(AccountFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountFixture.AN_IDUL)).thenReturn(false);
        Mockito.when(mapper.create(request)).thenReturn(account);

        service.createAccount(request);

        Mockito.verify(repository).save(account);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowConflictException() {
        Mockito.when(repository.existsByEmail(AccountFixture.AN_EMAIL)).thenReturn(true);

        Executable act = () -> service.createAccount(request);

        Assertions.assertThrows(ConflictException.class, act);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowConflictException() {
        Mockito.when(repository.existsByEmail(AccountFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountFixture.AN_IDUL)).thenReturn(true);

        Executable accountCreationAttempt = () -> service.createAccount(request);

        Assertions.assertThrows(ConflictException.class, accountCreationAttempt);
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowInvalidParameterException() {
        mockInvalidLoginSetup();

        Executable loginAttempt =
                () -> service.login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(InvalidParameterException.class, loginAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnAuthenticationToken() {
        mockValidLoginSetup();

        AuthenticationToken token =
                service.login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Assertions.assertEquals(AccountFixture.AN_AUTH_TOKEN, token);
    }

    private void mockInvalidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL)).thenReturn(account);
        Mockito.when(account.getPassword()).thenReturn(AccountFixture.A_PASSWORD);
        Mockito.when(AccountFixture.A_PASSWORD.matches(AccountFixture.A_RAW_PASSWORD))
                .thenReturn(false);
    }

    private void mockValidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL)).thenReturn(account);

        Mockito.when(account.getPassword()).thenReturn(AccountFixture.A_PASSWORD);
        Mockito.when(AccountFixture.A_PASSWORD.matches(AccountFixture.A_RAW_PASSWORD))
                .thenReturn(true);

        Mockito.when(account.getIdul()).thenReturn(AccountFixture.AN_IDUL);
        Mockito.when(authService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);
    }

    private CreateAccount aCreateAccountRequest() {
        return new CreateAccount(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
