package ca.ulaval.glo4003.trotti.order.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BillingFrequencyTest {
    private static final String PER_TRIP_STRING = "per trip";
    private static final String PER_TRIP_ALT = "per_trip";
    private static final String PER_TRIP_NORMALIZED = "per-trip";
    private static final String MONTHLY_STRING = "monthly";
    private static final String MONTHLY_ALT = "per month";
    private static final String MONTHLY_UNDERSCORE = "per_month";
    private static final String MONTHLY_NORMALIZED = "per-month";
    private static final String INVALID_STRING = "weekly";

    @Test
    void givenPerTripVariants_whenFromString_thenReturnPerTripEnum() {
        BillingFrequency fromRegularPerTripString = BillingFrequency.fromString(PER_TRIP_STRING);
        BillingFrequency fromAlternatePerTripString = BillingFrequency.fromString(PER_TRIP_ALT);
        BillingFrequency fromNormalizedPerTripString =
                BillingFrequency.fromString(PER_TRIP_NORMALIZED);

        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromRegularPerTripString);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromAlternatePerTripString);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromNormalizedPerTripString);
    }

    @Test
    void givenMonthlyVariants_whenFromString_thenReturnMonthlyEnum() {
        BillingFrequency fromRegularMonthlyString = BillingFrequency.fromString(MONTHLY_STRING);
        BillingFrequency fromAlternateMonthlyString = BillingFrequency.fromString(MONTHLY_ALT);
        BillingFrequency fromUnderscoreMonthlyString =
                BillingFrequency.fromString(MONTHLY_UNDERSCORE);
        BillingFrequency fromNormalizedMonthlyString =
                BillingFrequency.fromString(MONTHLY_NORMALIZED);

        Assertions.assertEquals(BillingFrequency.MONTHLY, fromRegularMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromAlternateMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromUnderscoreMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromNormalizedMonthlyString);
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowsException() {
        Executable executable = () -> BillingFrequency.fromString(INVALID_STRING);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
