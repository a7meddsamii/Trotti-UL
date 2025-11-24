package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Currency;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import java.time.Duration;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DailyBillingUsageTest {

    private static final Duration TEN_MINUTES = Duration.ofMinutes(10);
    private static final Duration TWENTY_MINUTES = Duration.ofMinutes(20);
    private static final Duration THIRTY_MINUTES = Duration.ofMinutes(30);
    private static final Duration THIRTY_FIVE_MINUTES = Duration.ofMinutes(35);
    private static final Duration FIFTY_MINUTES = Duration.ofMinutes(50);
    private static final Duration SIXTY_MINUTES = Duration.ofMinutes(60);
    private static final Duration ONE_HOUR = SIXTY_MINUTES;
    private static final Duration ZERO_DURATION = Duration.ZERO;

    private static final Money ZERO_CAD = Money.zeroCad();
    private static final Money EXCEED_FEE = Money.of(5.00, Currency.CAD);

    private static final LocalDate TODAY = LocalDate.now();

    @Test
    void givenNewInstance_whenNoTravelTimeAndZeroBalance_thenIsEmptyTrue() {
        DailyBillingUsage usage = newUsage(ONE_HOUR);
        assertTrue(usage.isEmpty());
    }

    @Test
    void givenTravelTimeAdded_whenBelowOrEqualDailyLimit_thenNoFeeAdded() {
        DailyBillingUsage usage = newUsage(ONE_HOUR);
        usage.addTravelTime(THIRTY_MINUTES);

        usage.addTravelTime(THIRTY_MINUTES);

        assertEquals(ONE_HOUR, usage.getTravelingTime());
        assertEquals(ZERO_CAD, usage.getBalance());
    }

    @Test
    void givenTravelTimeCrossesLimit_whenAddingTravelTime_thenAddsExceedFeeToBalance() {
        DailyBillingUsage usage = newUsage(ONE_HOUR);
        usage.addTravelTime(FIFTY_MINUTES);

        usage.addTravelTime(THIRTY_MINUTES);

        assertEquals(EXCEED_FEE, usage.getBalance());
        assertFalse(usage.isEmpty());
    }

    @Test
    void givenLimitAlreadyExceeded_whenAddingTravelTimeAgain_thenSingleExceedFeeAdded() {
        DailyBillingUsage usage = newUsage(TWENTY_MINUTES);
        usage.addTravelTime(THIRTY_MINUTES);

        usage.addTravelTime(TEN_MINUTES);

        assertEquals(EXCEED_FEE, usage.getBalance());
    }

    @Test
    void givenAlreadyExceeded_whenAddingMoreTravelTime_thenFeeNotAddedAgain() {
        DailyBillingUsage usage = newUsage(ONE_HOUR);
        usage.addTravelTime(FIFTY_MINUTES);
        usage.addTravelTime(TWENTY_MINUTES);
        Money balanceAfterFirstExceed = usage.getBalance();

        usage.addTravelTime(TEN_MINUTES);

        assertEquals(balanceAfterFirstExceed, usage.getBalance());
    }

    @Test
    void whenAddingMultipleTravelTimes_thenTravelingTimeAccumulates() {
        DailyBillingUsage usage = newUsage(THIRTY_FIVE_MINUTES);
        usage.addTravelTime(TWENTY_MINUTES);
        usage.addTravelTime(THIRTY_MINUTES);

        usage.addTravelTime(TEN_MINUTES);

        assertEquals(SIXTY_MINUTES, usage.getTravelingTime());
    }

    @Test
    void givenMatchingDate_whenIsOnDate_thenReturnsTrue() {
        DailyBillingUsage usage =
                new DailyBillingUsage(THIRTY_MINUTES, TODAY, ZERO_DURATION, ZERO_CAD, false);
        assertTrue(usage.isOnDate(TODAY));
    }

    @Test
    void givenDifferentDate_whenIsOnDate_thenReturnsFalse() {
        DailyBillingUsage usage = new DailyBillingUsage(THIRTY_MINUTES, TODAY.minusDays(1),
                ZERO_DURATION, ZERO_CAD, false);
        assertFalse(usage.isOnDate(TODAY));
    }

    private DailyBillingUsage newUsage(Duration maxPerDay) {
        return new DailyBillingUsage(maxPerDay, TODAY, ZERO_DURATION, ZERO_CAD, false);
    }
}
