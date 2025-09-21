package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class AccountTest {

    private static final LocalDate A_GIVEN_BIRTHDAY = LocalDate.of(2001, 02, 10);
    private static final int EXPECTED_AGE = 24;

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        Account account = new AccountFixture()
                .withBirthDate(A_GIVEN_BIRTHDAY)
                .build();

        Assertions.assertEquals(EXPECTED_AGE, account.getAge());
    }
}
