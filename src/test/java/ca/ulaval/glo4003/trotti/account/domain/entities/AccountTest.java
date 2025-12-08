package ca.ulaval.glo4003.trotti.account.domain.entities;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTest {

    private static final LocalDate GIVEN_BIRTHDATE = LocalDate.of(2001, Month.FEBRUARY, 10);
    private static final int EXPECTED_AGE = 24;

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        // given
        Account account = new AccountFixture().withBirthDate(GIVEN_BIRTHDATE).build();

        // when
        int actualAge = account.getAge();

        // then
        Assertions.assertEquals(EXPECTED_AGE, actualAge);
    }
}
