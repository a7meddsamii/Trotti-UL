package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
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
        request = AccountServiceFixture.aCreateAccountRequest();
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenMapperCreateIsCalled() {
        Mockito.when(repository.existsByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountServiceFixture.AN_IDUL)).thenReturn(false);

        service.createAccount(request);

        Mockito.verify(mapper).create(request);
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenRepositorySaveIsCalled() {
        Mockito.when(repository.existsByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountServiceFixture.AN_IDUL)).thenReturn(false);
        Mockito.when(mapper.create(request)).thenReturn(account);

        service.createAccount(request);

        Mockito.verify(repository).save(account);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowConflictException() {
        Mockito.when(repository.existsByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(true);

        Executable act = () -> service.createAccount(request);

        Assertions.assertThrows(ConflictException.class, act);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowConflictException() {
        Mockito.when(repository.existsByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(false);
        Mockito.when(repository.existsByIdul(AccountServiceFixture.AN_IDUL)).thenReturn(true);

        Executable accountCreationAttempt = () -> service.createAccount(request);

        Assertions.assertThrows(ConflictException.class, accountCreationAttempt);
    }

    @Test
    void givenInvalidPassword_whenLogin_thenThrowInvalidParameterException() {
        mockInvalidLoginSetup();

        Executable loginAttempt = () -> service.login(AccountServiceFixture.AN_EMAIL_STRING,
                AccountServiceFixture.A_RAW_PASSWORD);

        Assertions.assertThrows(InvalidParameterException.class, loginAttempt);
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnAuthenticationToken() {
        mockValidLoginSetup();

        AuthenticationToken token = service.login(AccountServiceFixture.AN_EMAIL_STRING,
                AccountServiceFixture.A_RAW_PASSWORD);

        Assertions.assertEquals(AccountServiceFixture.AN_AUTH_TOKEN, token);
    }

    private void mockInvalidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(account);
        Mockito.when(account.getPassword()).thenReturn(AccountServiceFixture.A_PASSWORD);
        Mockito.when(AccountServiceFixture.A_PASSWORD.matches(AccountServiceFixture.A_RAW_PASSWORD))
                .thenReturn(false);
    }

    private void mockValidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountServiceFixture.AN_EMAIL)).thenReturn(account);

        Mockito.when(account.getPassword()).thenReturn(AccountServiceFixture.A_PASSWORD);
        Mockito.when(AccountServiceFixture.A_PASSWORD.matches(AccountServiceFixture.A_RAW_PASSWORD))
                .thenReturn(true);

        Mockito.when(account.getIdul()).thenReturn(AccountServiceFixture.AN_IDUL);
        Mockito.when(authService.generateToken(AccountServiceFixture.AN_IDUL))
                .thenReturn(AccountServiceFixture.AN_AUTH_TOKEN);
    }

}
