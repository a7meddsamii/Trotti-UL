package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import org.junit.jupiter.api.BeforeEach;

class AccountTest {

    private Account account;

    @BeforeEach
    void setup() {
        account = AccountFixture.createAccount();
    }

    // @Test
    // void givenBirthDate20YearsAgo_whenGetAge_thenReturnExpectedAge() {
    // LocalDate today = LocalDate.of(2025, 01, 12);
    // int expectedAge = Period.between(AccountFixture.A_BIRTHDATE, today).getYears();
    //
    // Assertions.assertEquals(expectedAge, account.getAge());
    // }
}
