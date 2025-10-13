package ca.ulaval.glo4003.trotti.domain.scooter.values;

import java.math.BigDecimal;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BatteryTest {
    private static final BigDecimal INITIAL_VALUE = BigDecimal.valueOf(50);
    private final Battery battery = Battery.from(INITIAL_VALUE);

    @Test
    void whenValueIsNegative_thenBatteryValueIsZero() {
        Battery result = Battery.from(BigDecimal.valueOf(-10));

        Assertions.assertEquals(Battery.from(BigDecimal.ZERO), result);
    }

    @Test
    void whenValueIsAbove100_thenBatteryValueIs100() {
        Battery result = Battery.from(BigDecimal.valueOf(150));

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(100)), result);
    }

    @Test
    void whenValueIsWithinRange_thenBatteryShouldKeepSameValue() {
        Battery result = Battery.from(BigDecimal.valueOf(80));

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(80)), result);
    }
	
    @Test
    void givenPositiveDelta_whenApplyDelta_thenValueIncreases() {
        BigDecimal delta = BigDecimal.valueOf(15);

        Battery result = battery.applyDelta(delta);

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(65)), result);
    }

    @Test
    void givenNegativeDelta_whenApplyDelta_thenValueDecreases() {
        BigDecimal delta = BigDecimal.valueOf(-20);

        Battery result = battery.applyDelta(delta);

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(30)), result);
    }

    @Test
    void givenDeltaThatExceedsMax_whenApplyDelta_thenClampedTo100() {
        BigDecimal delta = BigDecimal.valueOf(100);

        Battery result = battery.applyDelta(delta);

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(100)), result);
    }

    @Test
    void givenDeltaThatExceedsMin_whenApplyDelta_thenClampedTo0() {
        BigDecimal delta = BigDecimal.valueOf(-100);

        Battery result = battery.applyDelta(delta);

        Assertions.assertEquals(Battery.from(BigDecimal.ZERO), result);
    }

    @Test
    void givenZeroDelta_whenApplyDelta_thenValueUnchanged() {
        BigDecimal delta = BigDecimal.ZERO;

        Battery result = battery.applyDelta(delta);

        Assertions.assertEquals(Battery.from(BigDecimal.valueOf(50)), result);
    }

    @Test
    void givenBatteryWithLowerValue_whenIsGreaterThan_thenReturnTrue() {
        Battery lowerBattery = Battery.from(BigDecimal.valueOf(30));

        boolean result = battery.isGreaterThan(lowerBattery);

        Assertions.assertTrue(result);
    }

    @Test
    void givenBatteryWithHigherValue_whenIsGreaterThan_thenReturnFalse() {
        Battery higherBattery = Battery.from(BigDecimal.valueOf(80));

        boolean result = battery.isGreaterThan(higherBattery);

        Assertions.assertFalse(result);
    }

    @Test
    void givenBatteryWithEqualValue_whenIsGreaterThan_thenReturnFalse() {
        Battery equalBattery = Battery.from(BigDecimal.valueOf(50));

        boolean result = battery.isGreaterThan(equalBattery);

        Assertions.assertFalse(result);
    }

    @Test
    void whenToString_thenReturnNumericStringRepresentation() {
        String result = battery.toString();

        Assertions.assertEquals("50", result);
    }

    @Test
    void givenSameBatteryValue_whenEquals_thenReturnTrue() {
        Battery anotherBattery = Battery.from(BigDecimal.valueOf(50));

        Assertions.assertEquals(battery, anotherBattery);
    }

    @Test
    void givenDifferentBatteryValue_whenEquals_thenReturnFalse() {
        Battery anotherBattery = Battery.from(BigDecimal.valueOf(72));

        Assertions.assertNotEquals(battery, anotherBattery);
    }

    @Test
    void givenSameBatteryValue_whenHashCode_thenReturnSameValue() {
        Battery anotherBattery = Battery.from(BigDecimal.valueOf(50));

        Assertions.assertEquals(battery.hashCode(), anotherBattery.hashCode());
    }

    @Test
    void givenDifferentBatteryValue_whenHashCode_thenReturnDifferentValue() {
        Battery anotherBattery = Battery.from(BigDecimal.valueOf(72));

        Assertions.assertNotEquals(battery.hashCode(), anotherBattery.hashCode());
    }
}
