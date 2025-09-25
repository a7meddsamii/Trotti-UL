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

    private static final String PER_TRIP_LABEL = "Per trip";
    private static final String MONTHLY_LABEL = "Monthly";

    @Test
    void givenPerTripVariants_whenFromString_thenReturnPerTripEnum() {
        Assertions.assertEquals(BillingFrequency.PER_TRIP,
                BillingFrequency.fromString(PER_TRIP_STRING));
        Assertions.assertEquals(BillingFrequency.PER_TRIP,
                BillingFrequency.fromString(PER_TRIP_ALT));
        Assertions.assertEquals(BillingFrequency.PER_TRIP,
                BillingFrequency.fromString(PER_TRIP_NORMALIZED));
    }

    @Test
    void givenMonthlyVariants_whenFromString_thenReturnMonthlyEnum() {
        Assertions.assertEquals(BillingFrequency.MONTHLY,
                BillingFrequency.fromString(MONTHLY_STRING));
        Assertions.assertEquals(BillingFrequency.MONTHLY, BillingFrequency.fromString(MONTHLY_ALT));
        Assertions.assertEquals(BillingFrequency.MONTHLY,
                BillingFrequency.fromString(MONTHLY_UNDERSCORE));
        Assertions.assertEquals(BillingFrequency.MONTHLY,
                BillingFrequency.fromString(MONTHLY_NORMALIZED));
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowInvalidParameterException() {
        Executable executable = () -> BillingFrequency.fromString(INVALID_STRING);
        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenEnumConstant_whenToString_thenReturnExpectedLabel() {
        Assertions.assertEquals(PER_TRIP_LABEL, BillingFrequency.PER_TRIP.toString());
        Assertions.assertEquals(MONTHLY_LABEL, BillingFrequency.MONTHLY.toString());
    }

    @Test
    void givenInvalidString_whenFromString_thenExceptionMessageContainsAcceptedValues() {
        Executable executable = () -> BillingFrequency.fromString(INVALID_STRING);

        InvalidParameterException exception =
                Assertions.assertThrows(InvalidParameterException.class, executable);

        Assertions
                .assertTrue(exception.getMessage().contains(BillingFrequency.PER_TRIP.toString()));
        Assertions.assertTrue(exception.getMessage().contains(BillingFrequency.MONTHLY.toString()));
    }
}
