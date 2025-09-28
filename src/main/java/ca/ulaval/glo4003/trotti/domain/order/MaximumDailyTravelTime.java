package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.Duration;
import java.util.Objects;

public class MaximumDailyTravelTime {
    private static final int MINUTES_IN_A_DAY = 24 * 60;

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

    private void validate(Duration duration) {
        if (duration == null) {
            throw new InvalidParameterException(
                    "Maximum daily travel time in minutes cannot be null");
        }

        long travelTimeInMinutes = duration.toMinutes();

        if (travelTimeInMinutes <= 0 || travelTimeInMinutes >= MINUTES_IN_A_DAY
                || !isMultipleOfTen(travelTimeInMinutes)) {
            throw new InvalidParameterException(String.format(
                    "Maximum daily travel time must be positive, a multiple of 10, and less than %d minutes.",
                    MINUTES_IN_A_DAY));
        }
    }

    private boolean isMultipleOfTen(long travelTimeInMinutes) {
        return travelTimeInMinutes % 10 == 0;
    }
}
