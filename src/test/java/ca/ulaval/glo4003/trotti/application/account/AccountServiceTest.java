package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.shared.exception.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class AccountServiceTest {
    private PasswordHasher hasher;
    private Password password;

    private AccountRepository repository;
    private AccountMapper mapper;
    private AuthenticationService authService;
    private AccountService service;

    private CreateAccount request;
    private Account account;

    @BeforeEach
    void setup() {
        hasher = Mockito.mock(PasswordHasher.class);
        password = Mockito.mock(Password.class);

        repository = Mockito.mock(AccountRepository.class);
        mapper = Mockito.mock(AccountMapper.class);
        authService = Mockito.mock(AuthenticationService.class);
        service = new AccountService(repository, mapper, authService);

        account = Mockito.mock(Account.class);
        request = aCreateAccountRequest();
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenMapperCreateIsCalled() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL)).thenReturn(Optional.empty());
        Mockito.when(repository.findByIdul(AccountFixture.AN_IDUL)).thenReturn(Optional.empty());
        Mockito.when(mapper.create(request)).thenReturn(account);

        service.createAccount(request);

        Mockito.verify(mapper).create(request);
    }

    @Test
    void givenNewAccount_whenCreateAccount_thenRepositorySaveIsCalled() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL)).thenReturn(Optional.empty());
        Mockito.when(repository.findByIdul(AccountFixture.AN_IDUL)).thenReturn(Optional.empty());
        Mockito.when(mapper.create(request)).thenReturn(account);

        service.createAccount(request);

        Mockito.verify(repository).save(account);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));

        Executable act = () -> service.createAccount(request);

        Assertions.assertThrows(AlreadyExistsException.class, act);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL)).thenReturn(Optional.empty());
        Mockito.when(repository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(account)));

        Executable accountCreationAttempt = () -> service.createAccount(request);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenThrowException() {
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

    @Test
    void givenValidCredentials_whenLogin_thenAuthServiceGenerateTokenIsCalled() {
        mockValidLoginSetup();

        service.login(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(authService).generateToken(AccountFixture.AN_IDUL);
    }

    private void mockInvalidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));
        Mockito.when(account.getPassword()).thenReturn(password);
        Mockito.when(password.matches(AccountFixture.A_RAW_PASSWORD)).thenReturn(false);
    }

    private void mockValidLoginSetup() {
        Mockito.when(repository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(account));

        Mockito.when(account.getPassword()).thenReturn(password);
        Mockito.when(password.matches(AccountFixture.A_RAW_PASSWORD)).thenReturn(true);

        Mockito.when(account.getIdul()).thenReturn(AccountFixture.AN_IDUL);
        Mockito.when(authService.generateToken(AccountFixture.AN_IDUL))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);
    }

    private CreateAccount aCreateAccountRequest() {
        return new CreateAccount(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE_STRING,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
