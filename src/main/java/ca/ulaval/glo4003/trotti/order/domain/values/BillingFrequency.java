package ca.ulaval.glo4003.trotti.order.domain.values;

import ca.ulaval.glo4003.trotti.commons.api.exceptions.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum BillingFrequency {
    PER_TRIP("Per trip"), MONTHLY("Monthly");

    private final String label;

    BillingFrequency(String label) {
        this.label = label;
    }

    public static BillingFrequency fromString(String value) {
        String normalizedValue = value.trim().toLowerCase().replaceAll("[\\s_]", "-");

        return switch (normalizedValue) {
            case "per-trip" -> PER_TRIP;
            case "monthly", "per-month" -> MONTHLY;
            default -> throw new InvalidParameterException("Invalid billing frequency: " + value
                    + ". Accepted values are: " + acceptedValues());
        };
    }

    @Override
    public String toString() {
        return label;
    }

    private static String acceptedValues() {
        return Arrays.stream(BillingFrequency.values()).map(BillingFrequency::toString)
                .collect(Collectors.joining(", "));
    }
}
