package ca.ulaval.glo4003.trotti.application.mapper;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountMapperTest {
    private AccountFactory factory;
    private PasswordHasher hasher;
    private AccountMapper mapper;

    private CreateAccount request;
    private Account account;

    @BeforeEach
    void setup() {
        factory = Mockito.mock(AccountFactory.class);
        hasher = Mockito.mock(PasswordHasher.class);

        mapper = new AccountMapper(factory, hasher);
        request = new CreateAccount(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
        account = Mockito.mock(Account.class);
    }

    @Test
    void givenValidRequest_whenCreate_thenCallsFactoryCreateWithExpectedValues() {
        mapper.create(request);

        Mockito.verify(factory).create(Mockito.eq(AccountFixture.A_NAME),
                Mockito.eq(AccountFixture.A_BIRTHDATE), Mockito.eq(AccountFixture.A_GENDER),
                Mockito.eq(AccountFixture.AN_IDUL), Mockito.eq(AccountFixture.AN_EMAIL),
                Mockito.any(Password.class));
    }

    @Test
    void givenValidRequest_whenCreate_thenReturnsAccountFromFactory() {
        mockFactoryToReturnAccount();

        Account result = mapper.create(request);

        Assertions.assertEquals(account, result);
    }

    private void mockFactoryToReturnAccount() {
        Mockito.when(factory.create(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(account);
    }
}
