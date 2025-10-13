package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions.InvalidBatteryValue;

import java.math.BigDecimal;
import java.util.Objects;

public class Battery {
	private static final BigDecimal MIN_BATTERY_VALUE = BigDecimal.ZERO;
	private static final BigDecimal MAX_BATTERY_VALUE = BigDecimal.valueOf(100);
	private final BigDecimal value;
	
	public static Battery from(BigDecimal value) {
		return new Battery(value);
	}
	
	private Battery(BigDecimal value) {
		validateBatteryValue(value);
		this.value = value;
	}
	
	private void validateBatteryValue(BigDecimal value) {
		if (value.compareTo(MIN_BATTERY_VALUE) < 0 || value.compareTo(MAX_BATTERY_VALUE) > 0) {
			throw new InvalidBatteryValue("Battery value must be between" + MIN_BATTERY_VALUE + " and " + MAX_BATTERY_VALUE);
		}
	}
	
	public Battery applyDelta(BigDecimal delta) {
		BigDecimal newValue = value.add(delta);
		
		if (newValue.compareTo(MIN_BATTERY_VALUE) < 0) {
			newValue = MIN_BATTERY_VALUE;
		}
		else if (newValue.compareTo(MAX_BATTERY_VALUE) > 0) {
			newValue = MAX_BATTERY_VALUE;
		}
		
		return Battery.from(newValue);
	}
	
	public boolean isGreaterThan(Battery that) {
		return this.value.compareTo(that.value) > 0;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		Battery battery = (Battery) o;
		
		return value.compareTo(battery.value) == 0;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
