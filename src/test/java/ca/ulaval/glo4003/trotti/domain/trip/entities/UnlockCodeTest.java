package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeTest {

    private static final Instant START_MOMENT = Instant.parse("2025-10-13T10:00:00Z");
    private static final Instant FUTURE_TIME_EXPIRED = START_MOMENT.plusSeconds(61);
    private static final String NUMERIC_REGEX = "\\d+";
    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static final Idul ANOTHER_TRAVELER_ID = Idul.from("anotherTravelerId");

    private Clock clock;
    private UnlockCode unlockCode;

    @BeforeEach
    void setup() {
        clock = Mockito.spy(Clock.fixed(START_MOMENT, ZoneOffset.UTC));
        unlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID, clock);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs4DigistsOrMore() {
        Assertions.assertNotNull(unlockCode);
        Assertions.assertTrue(unlockCode.getCode().length() >= 4);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs6DigitsOrLess() {
        Assertions.assertTrue(unlockCode.getCode().length() <= 6);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeIsNumeric() {
        Assertions.assertTrue(unlockCode.getCode().matches(NUMERIC_REGEX));
    }

    @Test
    void whenCreatingTwoUnlockCodes_thenTheyAreDifferent() {
        UnlockCode firstUnlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID, clock);
        UnlockCode secondUnlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID, clock);

        Assertions.assertNotEquals(firstUnlockCode.getCode(), secondUnlockCode.getCode());
    }

    @Test
    void whenCreatingUnlockCode_thenItIsNotExpired() {
        Assertions.assertFalse(unlockCode.isExpired());
    }

    @Test
    void givenUnlockCodeAndSixtySecondsPassed_whenIsExpired_thenItIsExpired() {
        Mockito.when(clock.instant()).thenReturn(FUTURE_TIME_EXPIRED);
        Assertions.assertTrue(unlockCode.isExpired());
    }

    @Test
    void givenCorrectCodeValue_whenIsCorrectValue_thenReturnsTrue() {
        Assertions.assertTrue(unlockCode.isCorrectValue(unlockCode));
    }

    @Test
    void givenDifferentCodeValue_whenIsCorrectValue_thenReturnsFalse() {
        UnlockCode differentUnlockCode =
                UnlockCode.generateFromTravelerId(ANOTHER_TRAVELER_ID, clock);

        Assertions.assertFalse(unlockCode.isCorrectValue(differentUnlockCode));
    }
}
