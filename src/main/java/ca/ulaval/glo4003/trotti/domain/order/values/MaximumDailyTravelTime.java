package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Currency;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

public class MaximumDailyTravelTime {
    private static final int MINUTES_IN_A_DAY = 24 * 60;
    private static final int MINIMUM_TRAVEL_TIME_IN_MINUTES = 30;
    private static final Money BASE_PRICE = Money.of(new BigDecimal(45), Currency.CAD);
    private static final Money PRICE_PER_ADDITIONAL_TEN_MINUTES =
            Money.of(new BigDecimal(2), Currency.CAD);

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
        if (duration == null || duration.isNegative() || duration.isZero()) {
            throw new InvalidParameterException(
                    "Maximum daily travel time in minutes cannot be null and must be positive.");
        }

        long travelTimeInMinutes = duration.toMinutes();

        if (travelTimeInMinutes < MINIMUM_TRAVEL_TIME_IN_MINUTES
                || travelTimeInMinutes >= MINUTES_IN_A_DAY
                || !isMultipleOfTen(travelTimeInMinutes)) {
            throw new InvalidParameterException(String.format(
                    "Maximum daily travel time must be at least 30 minutes, multiple of 10, and less than %d minutes.",
                    MINUTES_IN_A_DAY));
        }
    }

    private boolean isMultipleOfTen(long travelTimeInMinutes) {
        return travelTimeInMinutes % 10 == 0;
    }

    public Money calculateAmount() {
        long additionalMinutes = duration.toMinutes() - MINIMUM_TRAVEL_TIME_IN_MINUTES;
        long tenMinuteBlocks = additionalMinutes / 10;

        return BASE_PRICE.plus(
                PRICE_PER_ADDITIONAL_TEN_MINUTES.multiply(BigDecimal.valueOf(tenMinuteBlocks)));
    }
}
