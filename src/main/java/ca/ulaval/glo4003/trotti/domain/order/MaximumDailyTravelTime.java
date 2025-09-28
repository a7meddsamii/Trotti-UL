package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.Duration;
import java.util.Objects;

public class MaximumDailyTravelTime {
    private final Duration duration;

    private MaximumDailyTravelTime(Duration duration) {
        validate(duration);
        this.duration = duration;
    }

    public static MaximumDailyTravelTime from(Duration value) {
        return new MaximumDailyTravelTime(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaximumDailyTravelTime maximumDailyTravelTime = (MaximumDailyTravelTime) o;
        return duration == maximumDailyTravelTime.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }

    @Override
    public String toString() {
        return duration.toMinutes() + " minutes";
    }

    private void validate(Duration value) {
        long travelTimeInMinutes = value.toMinutes();

        if (travelTimeInMinutes < 0 || travelTimeInMinutes % 10 != 0) {
            throw new InvalidParameterException(
                    "Maximum daily travel time in minutes must be positive and multiple of 10");
        }
    }
}
