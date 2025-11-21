package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities;

import ca.ulaval.glo4003.trotti.payment.domain.values.money.Currency;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.time.Duration;
import java.time.LocalDate;

public class DailyBillingUsage {
    private static final Money DAILY_LIMIT_EXCEEDING_FEE = Money.of(5.00, Currency.CAD);
    private final Duration maximumTravelingTimePerDay;
    private final LocalDate date;
    private Duration travelingTime;
    private Money balance;
    private boolean maximumTravelingTimeExceeded;

    public DailyBillingUsage(
            Duration maximumTravelingTimePerDay,
            LocalDate date,
            Duration travelingTime,
            Money balance,
            boolean maximumTravelingTimeExceeded) {
        this.maximumTravelingTimePerDay = maximumTravelingTimePerDay;
        this.date = date;
        this.travelingTime = travelingTime;
        this.balance = balance;
        this.maximumTravelingTimeExceeded = maximumTravelingTimeExceeded;
    }

    public void addTravelTime(Duration travelingTime) {
        this.travelingTime = this.travelingTime.plus(travelingTime);

        if (!maximumTravelingTimeExceeded
                && this.travelingTime.compareTo(maximumTravelingTimePerDay) > 0) {
            this.balance = this.balance.plus(DAILY_LIMIT_EXCEEDING_FEE);
            this.maximumTravelingTimeExceeded = true;
        }
    }

    public boolean isEmpty() {
        return travelingTime.isZero() && balance.isZero();
    }

    public Duration getMaximumTravelingTimePerDay() {
        return maximumTravelingTimePerDay;
    }

    public boolean isOnDate(LocalDate date) {
        return this.date.equals(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public Duration getTravelingTime() {
        return travelingTime;
    }

    public Money getBalance() {
        return balance;
    }
}
