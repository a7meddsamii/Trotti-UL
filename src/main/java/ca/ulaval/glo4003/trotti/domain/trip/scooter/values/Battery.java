package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import java.math.BigDecimal;
import java.util.Objects;

public class Battery {
    private final BigDecimal value;

    private Battery(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            this.value = BigDecimal.ZERO;
        } else if (value.compareTo(BigDecimal.valueOf(100)) > 0) {
            this.value = BigDecimal.valueOf(100);
        } else {
            this.value = value;
        }
    }

    public static Battery from(BigDecimal value) {
        return new Battery(value);
    }

    public Battery applyDelta(BigDecimal delta) {
        BigDecimal newValue = value.add(delta);
		
        if (newValue.compareTo(BigDecimal.ZERO) < 0) {
            newValue = BigDecimal.ZERO;
        } else if (newValue.compareTo(BigDecimal.valueOf(100)) > 0) {
            newValue = BigDecimal.valueOf(100);
        }
		
        return Battery.from(newValue);
    }

    public boolean isGreaterThan(Battery that) {
        return this.value.compareTo(that.value) > 0;
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
