package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Currency;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Money;
import java.math.BigDecimal;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MaximumDailyTravelTimeTest {
    private static final Duration VALID_TIME = Duration.ofMinutes(30);
    private static final Duration ANOTHER_VALID_TIME = Duration.ofMinutes(60);
    private static final Duration NEGATIVE_TIME = Duration.ofMinutes(-10);
    private static final Duration LESS_THAN_10_MINUTES = Duration.ofMinutes(25);
    private static final Duration ZERO_TIME = Duration.ofMinutes(0);
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
    void givenZeroTime_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(ZERO_TIME);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenLessThan10MinutesTime_whenCreate_thenThrowsException() {
        Executable creation = () -> MaximumDailyTravelTime.from(LESS_THAN_10_MINUTES);

        Assertions.assertThrows(InvalidParameterException.class, creation);
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
    void givenValidTime_whenToString_thenReturntringValue() {
        MaximumDailyTravelTime time = MaximumDailyTravelTime.from(VALID_TIME);

        Assertions.assertEquals(VALID_TIME.toMinutes() + " minutes", time.toString());
    }

    @Test
    void givenDurationEqualToMinimum_whenCalculateAmount_thenReturnBasePrice() {
        MaximumDailyTravelTime time = MaximumDailyTravelTime.from(Duration.ofMinutes(30));

        Money actual = time.calculateAmount();

        Money expected = Money.of(new BigDecimal(45), Currency.CAD);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenDurationLessThanMinimumBaseMinutes_whenCalculateAmount_thenReturnsBasePrice() {
        MaximumDailyTravelTime time = MaximumDailyTravelTime.from(Duration.ofMinutes(10));

        Money actual = time.calculateAmount();

        Money expected = Money.of(new BigDecimal(45), Currency.CAD);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenDurationWithAdditionalMinutes_whenCalculateAmount_thenReturnBasePlusExtra() {
        MaximumDailyTravelTime time = MaximumDailyTravelTime.from(Duration.ofMinutes(40));

        Money actual = time.calculateAmount();

        Money expected = Money.of(new BigDecimal(47), Currency.CAD);
        Assertions.assertEquals(expected, actual);
    }
}
