package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
        Executable executable =
                () -> factory.create(AccountFixture.A_NAME, TODAY, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL, AccountFixture.A_PASSWORD);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenFutureBirthDate_whenCreateAccount_thenThrowsInvalidParameterException() {

        Executable executable =
                () -> factory.create(AccountFixture.A_NAME, FUTURE_DATE, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL, AccountFixture.A_PASSWORD);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenPastBirthDate_whenCreateAccount_thenNoExceptionIsThrow() {

        Executable executable =
                () -> factory.create(AccountFixture.A_NAME, PAST_DATE, AccountFixture.A_GENDER,
                        AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL, AccountFixture.A_PASSWORD);

        Assertions.assertDoesNotThrow(executable);
    }
}
