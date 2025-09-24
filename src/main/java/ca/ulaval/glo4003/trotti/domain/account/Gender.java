package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Gender {
    FEMALE("Female"), MALE("Male"), NON_BINARY("Non-binary"), UNSPECIFIED("Unspecified");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender fromString(String value) {
        String normalizedValue = value.trim().toLowerCase().replaceAll("[\\s_]", "-");

        return switch (normalizedValue) {
            case "male", "m" -> MALE;
            case "female", "f" -> FEMALE;
            case "non-binary", "nb" -> NON_BINARY;
            case "unspecified", "u" -> UNSPECIFIED;
            default -> throw new InvalidParameterException(
                    "Invalid gender: " + value + ". Accepted values are: " + acceptedValues());
        };
    }

    @Override
    public String toString() {
        return label;
    }

    private static String acceptedValues() {
        return Arrays.stream(Gender.values()).map(Gender::toString)
                .collect(Collectors.joining(", "));
    }
}
