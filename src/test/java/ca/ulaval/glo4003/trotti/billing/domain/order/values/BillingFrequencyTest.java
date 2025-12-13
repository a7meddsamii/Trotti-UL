package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BillingFrequencyTest {
    private static final String PER_TRIP_STANDARD = "per trip";
    private static final String PER_TRIP_UNDERSCORE = "per_trip";
    private static final String PER_TRIP_HYPHEN = "per-trip";
    private static final String MONTHLY_STANDARD = "monthly";
    private static final String MONTHLY_ALT = "per month";
    private static final String MONTHLY_UNDERSCORE = "per_month";
    private static final String MONTHLY_HYPHEN = "per-month";
    private static final String INVALID_VALUE = "weekly";

    @Test
    void givenPerTripVariants_whenFromString_thenReturnsPerTripEnum() {
        BillingFrequency fromRegularPerTripString = BillingFrequency.fromString(PER_TRIP_STANDARD);
        BillingFrequency fromAlternatePerTripString =
                BillingFrequency.fromString(PER_TRIP_UNDERSCORE);
        BillingFrequency fromNormalizedPerTripString = BillingFrequency.fromString(PER_TRIP_HYPHEN);

        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromRegularPerTripString);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromAlternatePerTripString);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, fromNormalizedPerTripString);
    }

    @Test
    void givenMonthlyVariants_whenFromString_thenReturnsMonthlyEnum() {
        BillingFrequency fromRegularMonthlyString = BillingFrequency.fromString(MONTHLY_STANDARD);
        BillingFrequency fromAlternateMonthlyString = BillingFrequency.fromString(MONTHLY_ALT);
        BillingFrequency fromUnderscoreMonthlyString =
                BillingFrequency.fromString(MONTHLY_UNDERSCORE);
        BillingFrequency fromNormalizedMonthlyString = BillingFrequency.fromString(MONTHLY_HYPHEN);

        Assertions.assertEquals(BillingFrequency.MONTHLY, fromRegularMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromAlternateMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromUnderscoreMonthlyString);
        Assertions.assertEquals(BillingFrequency.MONTHLY, fromNormalizedMonthlyString);
    }

    @Test
    void givenInvalidValue_whenFromString_thenThrowsException() {
        Executable executable = () -> BillingFrequency.fromString(INVALID_VALUE);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
