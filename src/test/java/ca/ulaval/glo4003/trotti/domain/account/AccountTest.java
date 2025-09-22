package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTest {

    private static final LocalDate A_GIVEN_BIRTHDATE = LocalDate.of(2001, 02, 10);
    private static final int EXPECTED_AGE = 24;

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        Account account = new AccountFixture().withBirthDate(A_GIVEN_BIRTHDATE).build();

        Assertions.assertEquals(EXPECTED_AGE, account.getAge());
    }
}
