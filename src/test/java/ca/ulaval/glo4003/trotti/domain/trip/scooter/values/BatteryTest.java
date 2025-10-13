package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import java.math.BigDecimal;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions.InvalidBatteryValue;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BatteryTest {
	private static final BigDecimal INITIAL_VALUE = BigDecimal.valueOf(50);
	private static final BigDecimal DIFFERENT_VALUE = BigDecimal.valueOf(22);
	private static final BigDecimal BELOW_MINIMUM_VALUE = BigDecimal.valueOf(-10);
	private static final BigDecimal ABOVE_MAXIMUM_VALUE = BigDecimal.valueOf(110);
	private static final BigDecimal POSITIVE_DELTA = BigDecimal.valueOf(15);
	private static final BigDecimal NEGATIVE_DELTA = BigDecimal.valueOf(-10);
	private static final BigDecimal MAX_BATTERY_VALUE = BigDecimal.valueOf(100);
	private static final BigDecimal MIN_BATTERY_VALUE = BigDecimal.ZERO;
	private Battery battery;
	
	@BeforeEach
	void setup() {
		battery = Battery.from(INITIAL_VALUE);
	}
	
	@Test
	void givenBelowMinimum_whenFrom_thenThrowException() {
		Executable creationWithNegativeValue = () -> Battery.from(BELOW_MINIMUM_VALUE);
		
		Assertions.assertThrows(InvalidBatteryValue.class, creationWithNegativeValue);
	}
	
	@Test
	void givenValueAboveMaximum_whenFrom_thenThrowException() {
		Executable creationWithAbove100 = () -> Battery.from(ABOVE_MAXIMUM_VALUE);
		
		Assertions.assertThrows(InvalidBatteryValue.class, creationWithAbove100);
	}
	
	@Test
	void givenPositiveDelta_whenApplyDelta_thenValueIncreases() {
		BigDecimal previousBatteryValue = battery.getValue();
		
		BigDecimal newBatteryValue = battery.applyDelta(POSITIVE_DELTA).getValue();
		
		Assertions.assertTrue(newBatteryValue.compareTo(previousBatteryValue) > 0);
	}
	
	@Test
	void givenNegativeDelta_whenApplyDelta_thenValueDecreases() {
		BigDecimal previousBatteryValue = battery.getValue();
		
		BigDecimal newBatteryValue = battery.applyDelta(NEGATIVE_DELTA).getValue();
		
		Assertions.assertTrue(newBatteryValue.compareTo(previousBatteryValue) < 0);
	}
	
	@Test
	void givenDeltaThatExceedsMax_whenApplyDelta_thenClampedToMaxBatteryValue() {
		Battery battery = Battery.from(MAX_BATTERY_VALUE);
		BigDecimal delta = BigDecimal.valueOf(1);
		
		BigDecimal newBatteryValue = battery.applyDelta(delta).getValue();
		
		Assertions.assertEquals(MAX_BATTERY_VALUE, newBatteryValue);
	}
	
	@Test
	void givenDeltaThatExceedsMin_whenApplyDelta_thenClampedToMinBatteryValue() {
		Battery battery = Battery.from(MIN_BATTERY_VALUE);
		BigDecimal delta = BigDecimal.valueOf(-1);
		
		BigDecimal newBatteryValue = battery.applyDelta(delta).getValue();
		
		Assertions.assertEquals(MIN_BATTERY_VALUE, newBatteryValue);
	}
	
	@Test
	void givenZeroDelta_whenApplyDelta_thenValueUnchanged() {
		BigDecimal previousBatteryValue = battery.getValue();
		
		BigDecimal newBatteryValue = battery.applyDelta(BigDecimal.ZERO).getValue();
		
		Assertions.assertEquals(previousBatteryValue, newBatteryValue);
	}
	
	@Test
	void givenBatteryWithLowerValue_whenIsGreaterThan_thenReturnTrue() {
		Battery lowerBattery = Battery.from(INITIAL_VALUE.subtract(BigDecimal.ONE));
		
		boolean greater = battery.isGreaterThan(lowerBattery);
		
		Assertions.assertTrue(greater);
	}
	
	@Test
	void givenBatteryWithHigherValue_whenIsGreaterThan_thenReturnFalse() {
		Battery higherBattery = Battery.from(INITIAL_VALUE.add(BigDecimal.ONE));
		
		boolean greater = battery.isGreaterThan(higherBattery);
		
		Assertions.assertFalse(greater);
	}
	
	@Test
	void givenBatteryWithEqualValue_whenIsGreaterThan_thenReturnFalse() {
		Battery equalBattery = Battery.from(INITIAL_VALUE);
		
		boolean greater = battery.isGreaterThan(equalBattery);
		
		Assertions.assertFalse(greater);
	}
	
	
	@Test
	void givenBatteryWithSameValue_whenEquals_thenReturnTrue() {
		Battery anotherBattery = Battery.from(INITIAL_VALUE);
		
		Assertions.assertEquals(battery, anotherBattery);
	}
	
	@Test
	void givenBatteryWithDifferentValue_whenEquals_thenReturnFalse() {
		Battery anotherBattery = Battery.from(DIFFERENT_VALUE);
		
		Assertions.assertNotEquals(battery, anotherBattery);
	}
	
	@Test
	void givenSameBatteryValue_whenHashCode_thenReturnSameValue() {
		Battery anotherBattery = Battery.from(INITIAL_VALUE);
		
		Assertions.assertEquals(battery.hashCode(), anotherBattery.hashCode());
	}
	
	@Test
	void givenDifferentBatteryValue_whenHashCode_thenReturnDifferentValue() {
		Battery anotherBattery = Battery.from(DIFFERENT_VALUE);
		
		Assertions.assertNotEquals(battery.hashCode(), anotherBattery.hashCode());
	}
}
