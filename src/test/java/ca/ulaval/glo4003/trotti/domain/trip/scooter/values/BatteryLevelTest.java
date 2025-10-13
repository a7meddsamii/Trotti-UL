package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions.InvalidBatteryValue;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BatteryLevelTest {
    private static final BigDecimal INITIAL_VALUE = BigDecimal.valueOf(50);
    private static final BigDecimal DIFFERENT_VALUE = BigDecimal.valueOf(22);
    private static final BigDecimal BELOW_MINIMUM_VALUE = BigDecimal.valueOf(-10);
    private static final BigDecimal ABOVE_MAXIMUM_VALUE = BigDecimal.valueOf(110);
    private static final BigDecimal POSITIVE_DELTA = BigDecimal.valueOf(15);
    private static final BigDecimal NEGATIVE_DELTA = BigDecimal.valueOf(-10);
    private static final BigDecimal MAX_BATTERY_VALUE = BigDecimal.valueOf(100);
    private static final BigDecimal MIN_BATTERY_VALUE = BigDecimal.ZERO;
    private BatteryLevel batteryLevel;

    @BeforeEach
    void setup() {
        batteryLevel = BatteryLevel.from(INITIAL_VALUE);
    }

    @Test
    void givenBelowMinimum_whenFrom_thenThrowException() {
        Executable creationWithNegativeValue = () -> BatteryLevel.from(BELOW_MINIMUM_VALUE);

        Assertions.assertThrows(InvalidBatteryValue.class, creationWithNegativeValue);
    }

    @Test
    void givenValueAboveMaximum_whenFrom_thenThrowException() {
        Executable creationWithAbove100 = () -> BatteryLevel.from(ABOVE_MAXIMUM_VALUE);

        Assertions.assertThrows(InvalidBatteryValue.class, creationWithAbove100);
    }

    @Test
    void givenPositiveDelta_whenApplyDelta_thenValueIncreases() {
        BigDecimal previousBatteryValue = batteryLevel.getValue();

        BigDecimal newBatteryValue = batteryLevel.applyDelta(POSITIVE_DELTA).getValue();

        Assertions.assertTrue(newBatteryValue.compareTo(previousBatteryValue) > 0);
    }

    @Test
    void givenNegativeDelta_whenApplyDelta_thenValueDecreases() {
        BigDecimal previousBatteryValue = batteryLevel.getValue();

        BigDecimal newBatteryValue = batteryLevel.applyDelta(NEGATIVE_DELTA).getValue();

        Assertions.assertTrue(newBatteryValue.compareTo(previousBatteryValue) < 0);
    }

    @Test
    void givenDeltaThatExceedsMax_whenApplyDelta_thenClampedToMaxBatteryValue() {
        BatteryLevel batteryLevel = BatteryLevel.from(MAX_BATTERY_VALUE);
        BigDecimal delta = BigDecimal.valueOf(1);

        BigDecimal newBatteryValue = batteryLevel.applyDelta(delta).getValue();

        Assertions.assertEquals(MAX_BATTERY_VALUE, newBatteryValue);
    }

    @Test
    void givenDeltaThatExceedsMin_whenApplyDelta_thenClampedToMinBatteryValue() {
        BatteryLevel batteryLevel = BatteryLevel.from(MIN_BATTERY_VALUE);
        BigDecimal delta = BigDecimal.valueOf(-1);

        BigDecimal newBatteryValue = batteryLevel.applyDelta(delta).getValue();

        Assertions.assertEquals(MIN_BATTERY_VALUE, newBatteryValue);
    }

    @Test
    void givenZeroDelta_whenApplyDelta_thenValueUnchanged() {
        BigDecimal previousBatteryValue = batteryLevel.getValue();

        BigDecimal newBatteryValue = batteryLevel.applyDelta(BigDecimal.ZERO).getValue();

        Assertions.assertEquals(previousBatteryValue, newBatteryValue);
    }

    @Test
    void givenBatteryWithLowerValue_whenIsGreaterThan_thenReturnTrue() {
        BatteryLevel lowerBatteryLevel = BatteryLevel.from(INITIAL_VALUE.subtract(BigDecimal.ONE));

        boolean greater = batteryLevel.isGreaterThan(lowerBatteryLevel);

        Assertions.assertTrue(greater);
    }

    @Test
    void givenBatteryWithHigherValue_whenIsGreaterThan_thenReturnFalse() {
        BatteryLevel higherBatteryLevel = BatteryLevel.from(INITIAL_VALUE.add(BigDecimal.ONE));

        boolean greater = batteryLevel.isGreaterThan(higherBatteryLevel);

        Assertions.assertFalse(greater);
    }

    @Test
    void givenBatteryWithEqualValue_whenIsGreaterThan_thenReturnFalse() {
        BatteryLevel equalBatteryLevel = BatteryLevel.from(INITIAL_VALUE);

        boolean greater = batteryLevel.isGreaterThan(equalBatteryLevel);

        Assertions.assertFalse(greater);
    }

    @Test
    void givenBatteryWithSameValue_whenEquals_thenReturnTrue() {
        BatteryLevel anotherBatteryLevel = BatteryLevel.from(INITIAL_VALUE);

        Assertions.assertEquals(batteryLevel, anotherBatteryLevel);
    }

    @Test
    void givenBatteryWithDifferentValue_whenEquals_thenReturnFalse() {
        BatteryLevel anotherBatteryLevel = BatteryLevel.from(DIFFERENT_VALUE);

        Assertions.assertNotEquals(batteryLevel, anotherBatteryLevel);
    }

    @Test
    void givenSameBatteryValue_whenHashCode_thenReturnSameValue() {
        BatteryLevel anotherBatteryLevel = BatteryLevel.from(INITIAL_VALUE);

        Assertions.assertEquals(batteryLevel.hashCode(), anotherBatteryLevel.hashCode());
    }

    @Test
    void givenDifferentBatteryValue_whenHashCode_thenReturnDifferentValue() {
        BatteryLevel anotherBatteryLevel = BatteryLevel.from(DIFFERENT_VALUE);

        Assertions.assertNotEquals(batteryLevel.hashCode(), anotherBatteryLevel.hashCode());
    }
}
