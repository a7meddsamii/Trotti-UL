package ca.ulaval.glo4003.trotti.domain.account;


import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidGenderException;

public enum Gender {
    FEMALE("Female"), MALE("Male"), NON_BINARY("Non-binary"), UNSPECIFIED("Unspecified");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Gender fromString(String value) {
        return switch (value.trim().toLowerCase()) {
            case "male", "m" -> MALE;
            case "female", "f" -> FEMALE;
            case "non-binary", "non_binary", "nb" -> NON_BINARY;
            case "unspecified", "u" -> UNSPECIFIED;
            default -> throw new InvalidGenderException(value);
        };
    }
}
