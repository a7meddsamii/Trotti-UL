package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountFactoryTest {

    private static final Instant START_MOMENT = Instant.parse("2025-09-20T00:00:00Z");
    private static final ZoneOffset UTC = ZoneOffset.UTC;

    private static final LocalDate TODAY = LocalDate.ofInstant(START_MOMENT, UTC);
    private static final LocalDate FUTURE_DATE = TODAY.plusDays(1);
    private static final LocalDate PAST_DATE = TODAY.minusYears(20);

    private AccountFactory factory;
    private Clock clock;

    @BeforeEach
    void setpu() {
        clock = Clock.fixed(START_MOMENT, UTC);
        factory = new AccountFactory(clock);
    }

    @Test
    void givenBirthDateToday_whenCreateAccount_thenThrowInvalidParameterException() {

        Assertions.assertThrows(InvalidParameterException.class,
                () -> factory.create(AccountFixture.A_NAME, TODAY, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                        AccountFixture.A_PASSWORD));
    }

    @Test
    void givenFutureBirthDate_whenCreateAccount_thenThrowsInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> factory.create(AccountFixture.A_NAME, FUTURE_DATE, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                        AccountFixture.A_PASSWORD));
    }

    @Test
    void givenPastBirthDate_whenCreateAccount_thenSucceeds() {
        Assertions.assertDoesNotThrow(() -> factory.create(AccountFixture.A_NAME, PAST_DATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                AccountFixture.A_PASSWORD));
    }
}
