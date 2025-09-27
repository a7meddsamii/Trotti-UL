package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
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
        BillingFrequency from_regular_per_trip_string =
                BillingFrequency.fromString(PER_TRIP_STRING);
        BillingFrequency from_alternate_per_trip_string = BillingFrequency.fromString(PER_TRIP_ALT);
        BillingFrequency from_normalized_per_trip_string =
                BillingFrequency.fromString(PER_TRIP_NORMALIZED);

        Assertions.assertEquals(BillingFrequency.PER_TRIP, from_regular_per_trip_string);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, from_alternate_per_trip_string);
        Assertions.assertEquals(BillingFrequency.PER_TRIP, from_normalized_per_trip_string);
    }

    @Test
    void givenMonthlyVariants_whenFromString_thenReturnMonthlyEnum() {
        BillingFrequency from_regular_monthly_string = BillingFrequency.fromString(MONTHLY_STRING);
        BillingFrequency from_alternate_monthly_string = BillingFrequency.fromString(MONTHLY_ALT);
        BillingFrequency from_underscore_monthly_string =
                BillingFrequency.fromString(MONTHLY_UNDERSCORE);
        BillingFrequency from_normalized_monthly_string =
                BillingFrequency.fromString(MONTHLY_NORMALIZED);

        Assertions.assertEquals(BillingFrequency.MONTHLY, from_regular_monthly_string);
        Assertions.assertEquals(BillingFrequency.MONTHLY, from_alternate_monthly_string);
        Assertions.assertEquals(BillingFrequency.MONTHLY, from_underscore_monthly_string);
        Assertions.assertEquals(BillingFrequency.MONTHLY, from_normalized_monthly_string);
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowsException() {
        Executable executable = () -> BillingFrequency.fromString(INVALID_STRING);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
