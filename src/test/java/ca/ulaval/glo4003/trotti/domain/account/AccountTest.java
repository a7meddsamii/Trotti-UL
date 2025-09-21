package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

    private static final Instant START_MOMENT = Instant.parse("2025-09-20T00:00:00Z");
    private static final ZoneOffset UTC = ZoneOffset.UTC;
    private static final LocalDate TODAY = LocalDate.ofInstant(START_MOMENT, UTC);
    private static final LocalDate TWENTY_YEARS_AGO = TODAY.minusYears(20);

    private Account accountWith20YearsAge;

    @BeforeEach
    void setup() {
        accountWith20YearsAge =
                new Account(AccountFixture.A_NAME, TWENTY_YEARS_AGO, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL, AccountFixture.A_PASSWORD);
    }

    @Test
    void givenBirthDate20YearsAgo_whenGetAge_thenReturn20() {
        int expectedAge = Period.between(TWENTY_YEARS_AGO, TODAY).getYears();

        Assertions.assertEquals(expectedAge, accountWith20YearsAge.getAge());
    }
}
