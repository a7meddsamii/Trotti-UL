package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import org.junit.jupiter.api.BeforeEach;

class AccountFactoryTest {

    private AccountFactory factory;

    @BeforeEach
    void setpu() {
        factory = new AccountFactory();
    }

    // @Test
    // void givenValidParameters_whenCreateAccount_thenReturnAccountWithExpectedValues() {
    // Account account = createAccountWithFactory();
    // // doesNotHrow
    // Assertions.assertEquals(AccountFixture.A_NAME, account.getName());
    // Assertions.assertEquals(AccountFixture.A_BIRTHDATE, account.getBirthDate());
    // Assertions.assertEquals(AccountFixture.A_GENDER, account.getGender());
    // Assertions.assertEquals(AccountFixture.AN_IDUL, account.getIdul());
    // Assertions.assertEquals(AccountFixture.AN_EMAIL, account.getEmail());
    // Assertions.assertEquals(AccountFixture.A_PASSWORD, account.getPassword());
    // }

    private Account createAccountWithFactory() {
        return factory.create(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_PASSWORD);
    }
}
