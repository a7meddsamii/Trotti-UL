package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryValue;
import java.math.BigDecimal;
import java.util.Objects;

public class BatteryLevel {
    private static final BigDecimal MIN_BATTERY_VALUE = BigDecimal.ZERO;
    private static final BigDecimal MAX_BATTERY_VALUE = BigDecimal.valueOf(100);
    private final BigDecimal value;

    public static BatteryLevel from(BigDecimal value) {
        return new BatteryLevel(value);
    }

    private BatteryLevel(BigDecimal value) {
        validateBatteryValue(value);
        this.value = value;
    }

    private void validateBatteryValue(BigDecimal value) {
        if (value.compareTo(MIN_BATTERY_VALUE) < 0 || value.compareTo(MAX_BATTERY_VALUE) > 0) {
            throw new InvalidBatteryValue("Battery value must be between" + MIN_BATTERY_VALUE
                    + " and " + MAX_BATTERY_VALUE);
        }
    }

    public BatteryLevel applyDelta(BigDecimal delta) {
        BigDecimal newValue = value.add(delta);

        if (newValue.compareTo(MIN_BATTERY_VALUE) < 0) {
            newValue = MIN_BATTERY_VALUE;
        } else if (newValue.compareTo(MAX_BATTERY_VALUE) > 0) {
            newValue = MAX_BATTERY_VALUE;
        }

        return BatteryLevel.from(newValue);
    }

    public boolean isGreaterThan(BatteryLevel that) {
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

        BatteryLevel batteryLevel = (BatteryLevel) o;

        return value.compareTo(batteryLevel.value) == 0;
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
