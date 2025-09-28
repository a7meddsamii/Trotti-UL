package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MaximumDailyTravelTimeTest {
    private static final Duration VALID_TIME = Duration.ofMinutes(30);
    private static final Duration ANOTHER_VALID_TIME = Duration.ofMinutes(60);
    private static final Duration NEGATIVE_TIME = Duration.ofMinutes(-10);
    private static final Duration SURPASSES_ONE_DAY_TIME = Duration.ofMinutes(1441);
    private static final Duration NOT_MULTIPLE_OF_TEN = Duration.ofMinutes(25);

    @Test
    void givenNullTime_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(null);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenValidTime_whenCreate_thenObjectIsCreated() {
        MaximumDailyTravelTime maximumDailyTravelTime = MaximumDailyTravelTime.from(VALID_TIME);

        Assertions.assertNotNull(maximumDailyTravelTime);
    }

    @Test
    void givenNegativeTime_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(NEGATIVE_TIME);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNotMultipleOfTen_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(NOT_MULTIPLE_OF_TEN);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTravelTimeMoreThan24Hours_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(SURPASSES_ONE_DAY_TIME);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoObjectsWithSameValue_whenCompare_thenTheyAreEqual() {
        MaximumDailyTravelTime time1 = MaximumDailyTravelTime.from(VALID_TIME);
        MaximumDailyTravelTime time2 = MaximumDailyTravelTime.from(VALID_TIME);

        Assertions.assertEquals(time1, time2);
        Assertions.assertEquals(time1.hashCode(), time2.hashCode());
    }

    @Test
    void givenTwoObjectsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        MaximumDailyTravelTime time1 = MaximumDailyTravelTime.from(VALID_TIME);
        MaximumDailyTravelTime time2 = MaximumDailyTravelTime.from(ANOTHER_VALID_TIME);

        Assertions.assertNotEquals(time1, time2);
    }

    @Test
    void givenValidTime_whenToString_thenReturnStringValue() {
        MaximumDailyTravelTime time = MaximumDailyTravelTime.from(VALID_TIME);

        Assertions.assertEquals(VALID_TIME.toMinutes() + " minutes", time.toString());
    }
}
