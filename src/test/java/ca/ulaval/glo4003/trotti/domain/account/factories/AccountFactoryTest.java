package ca.ulaval.glo4003.trotti.domain.account.factories;

import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountFactoryTest {

    private static final Instant START_MOMENT = Instant.parse("2025-09-20T00:00:00Z");
    private static final ZoneOffset UTC = ZoneOffset.UTC;
    private static final LocalDate TODAY = LocalDate.ofInstant(START_MOMENT, UTC);

    private static final int MINIMUM_ACCOUNT_AGE = 16;
    private static final LocalDate BIRTHDATE_EXACTLY_MINIMUM_AGE =
            TODAY.minusYears(MINIMUM_ACCOUNT_AGE);
    private static final LocalDate BIRTHDATE_YOUNGER_THAN_MINIMUM_AGE =
            BIRTHDATE_EXACTLY_MINIMUM_AGE.plusDays(1);
    private static final LocalDate BIRTHDATE_OLDER_THAN_MINIMUM_AGE =
            BIRTHDATE_EXACTLY_MINIMUM_AGE.minusYears(4);

    private AccountFactory accountFactory;
    private Password password;

    @BeforeEach
    void setup() {
        password = Mockito.mock(Password.class);
        Clock clock = Clock.fixed(START_MOMENT, UTC);
        accountFactory = new AccountFactory(clock);
    }

    @Test
    void givenBirthDateYoungerThanMinimumAge_whenCreateAccount_thenThrowsInvalidParameterException() {
        Executable createAccount = () -> accountFactory.create(AccountFixture.A_NAME,
                BIRTHDATE_YOUNGER_THAN_MINIMUM_AGE, AccountFixture.A_GENDER, AccountFixture.AN_IDUL,
                AccountFixture.AN_EMAIL, password);

        Assertions.assertThrows(InvalidParameterException.class, createAccount);
    }

    @Test
    void givenBirthDateExactlyMinimumAge_whenCreateAccount_thenDoesNotThrowException() {
        Executable createAccount = () -> accountFactory.create(AccountFixture.A_NAME,
                BIRTHDATE_EXACTLY_MINIMUM_AGE, AccountFixture.A_GENDER, AccountFixture.AN_IDUL,
                AccountFixture.AN_EMAIL, password);

        Assertions.assertDoesNotThrow(createAccount);
    }

    @Test
    void givenBirthDateOlderThanMinimumAge_whenCreateAccount_thenDoesNotThrowException() {
        Executable createAccount = () -> accountFactory.create(AccountFixture.A_NAME,
                BIRTHDATE_OLDER_THAN_MINIMUM_AGE, AccountFixture.A_GENDER, AccountFixture.AN_IDUL,
                AccountFixture.AN_EMAIL, password);

        Assertions.assertDoesNotThrow(createAccount);
    }
}
