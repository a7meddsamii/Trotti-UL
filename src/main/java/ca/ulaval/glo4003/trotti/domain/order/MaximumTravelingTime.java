package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.util.Objects;

public class MaximumTravelingTime {
    private final int value;

    private MaximumTravelingTime(int value) {
        validate(value);
        this.value = value;
    }

    public static MaximumTravelingTime from(int value) {
        return new MaximumTravelingTime(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaximumTravelingTime maximumTravelingTime = (MaximumTravelingTime) o;
        return value == maximumTravelingTime.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private void validate(int value) {

        if (value < 0 || value % 10 != 0) {
            throw new InvalidParameterException("Maximum traveling time is invalid");
        }
    }
}
