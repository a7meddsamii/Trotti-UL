package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
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
    private AccountDto accountDto;
    private Account mockAccount;

    private AccountService accountApplicationService;

    @BeforeEach
    void setup() {
        accountDto = createValidAccountDto();
        mockAccount = Mockito.mock(Account.class);
        accountRepository = Mockito.mock(AccountRepository.class);
        accountFactory = Mockito.mock(AccountFactory.class);
        sessionTokenProvider = Mockito.mock(SessionTokenProvider.class);
        accountApplicationService = new AccountApplicationService(accountRepository, accountFactory,
                sessionTokenProvider);
    }

    @Test
    void givenAccountDto_whenCreateAccount_thenAccountFactoryIsCalled() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createUserAccount(accountDto);

        Mockito.verify(accountFactory).create(accountDto.name(), accountDto.birthDate(),
                accountDto.gender(), accountDto.idul(), accountDto.email(), accountDto.password(),
                accountDto.role());
    }

    @Test
    void givenValidAccountDto_whenCreateAccount_thenAccountIsSavedInRepository() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();

        accountApplicationService.createUserAccount(accountDto);

        Mockito.verify(accountRepository).save(mockAccount);
    }

    @Test
    void givenValidAccountDto_whenCreateAccount_thenReturnIdul() {
        mockRepositoryToReturnNoExistingAccount();
        mockFactoryToReturnValidAccount();
        Mockito.when(mockAccount.getIdul()).thenReturn(AccountFixture.AN_IDUL);

        Idul idul = accountApplicationService.createUserAccount(accountDto);

        Assertions.assertEquals(accountDto.idul(), idul);
    }

    @Test
    void givenExistingEmail_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.of(mockAccount));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createUserAccount(accountDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    @Test
    void givenExistingIdul_whenCreateAccount_thenThrowAlreadyExistsException() {
        Mockito.when(accountRepository.findByEmail(AccountFixture.AN_EMAIL))
                .thenReturn(Optional.empty());
        Mockito.when(accountRepository.findByIdul(AccountFixture.AN_IDUL))
                .thenReturn((Optional.of(mockAccount)));

        Executable accountCreationAttempt =
                () -> accountApplicationService.createUserAccount(accountDto);

        Assertions.assertThrows(AlreadyExistsException.class, accountCreationAttempt);
    }

    private AccountDto createValidAccountDto() {
        return new AccountDto(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_PASSWORD, AccountFixture.A_ROLE);
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
                Mockito.any(Password.class), Mockito.eq(AccountFixture.A_ROLE)))
                .thenReturn(mockAccount);
    }
}
