package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MaximumTravelingTimeTest {
    private static final int VALID_TIME = 30;
    private static final int ANOTHER_VALID_TIME = 60;
    private static final int NEGATIVE_TIME = -10;
    private static final int NOT_MULTIPLE_OF_TEN = 25;

    @Test
    void givenValidTime_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> MaximumTravelingTime.from(VALID_TIME);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenNegativeTime_whenCreate_thenThrowInvalidParameterException() {
        Executable creation = () -> MaximumTravelingTime.from(NEGATIVE_TIME);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNotMultipleOfTen_whenCreate_thenThrowInvalidParameterException() {
        Executable creation = () -> MaximumTravelingTime.from(NOT_MULTIPLE_OF_TEN);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoObjectsWithSameValue_whenCompare_thenTheyAreEqual() {
        MaximumTravelingTime time1 = MaximumTravelingTime.from(VALID_TIME);
        MaximumTravelingTime time2 = MaximumTravelingTime.from(VALID_TIME);

        Assertions.assertEquals(time1, time2);
        Assertions.assertEquals(time1.hashCode(), time2.hashCode());
    }

    @Test
    void givenTwoObjectsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        MaximumTravelingTime time1 = MaximumTravelingTime.from(VALID_TIME);
        MaximumTravelingTime time2 = MaximumTravelingTime.from(ANOTHER_VALID_TIME);

        Assertions.assertNotEquals(time1, time2);
    }

    @Test
    void givenValidTime_whenToString_thenReturnStringValue() {
        MaximumTravelingTime time = MaximumTravelingTime.from(VALID_TIME);

        Assertions.assertEquals(String.valueOf(VALID_TIME), time.toString());
    }
}
