package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AccountValidatorTest {

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

    AccountValidator accountValidator;

    @BeforeEach
    void setup() {
        Clock clock = Clock.fixed(START_MOMENT, UTC);
        accountValidator = new AccountValidator(clock);
    }

    @Test
    void givenBirthDateYoungerThanMinimumAge_whenValidateBirthDate_thenThrowsException() {
        Executable tooYoungException =
                () -> accountValidator.validateBirthDate(BIRTHDATE_YOUNGER_THAN_MINIMUM_AGE);

        Assertions.assertThrows(InvalidParameterException.class, tooYoungException);
    }

    @Test
    void givenBirthDateExactlyMinimumAge_whenValidateBirthDate_thenDoesNotThrowException() {
        Executable exactAgeValidation =
                () -> accountValidator.validateBirthDate(BIRTHDATE_EXACTLY_MINIMUM_AGE);

        Assertions.assertDoesNotThrow(exactAgeValidation);
    }

    @Test
    void givenBirthDateOlderThanMinimumAge_whenValidateBirthDate_thenDoesNotThrowException() {
        Executable olderAgeValidation =
                () -> accountValidator.validateBirthDate(BIRTHDATE_OLDER_THAN_MINIMUM_AGE);

        Assertions.assertDoesNotThrow(olderAgeValidation);
    }

}
