package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {

    STUDENT("student"), EMPLOYEE("employee"), TECHNICIAN("technician"), ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role fromString(String value) {
        String normalizedValue = value.trim().toLowerCase();

        return switch (normalizedValue) {
            case "student" -> STUDENT;
            case "employee" -> EMPLOYEE;
            case "technician" -> TECHNICIAN;
            case "admin" -> ADMIN;
            default -> throw new InvalidParameterException(
                    "Invalid gender: " + value + ". Accepted values are: " + acceptedValues());
        };
    }

    @Override
    public String toString() {
        return role;
    }

    private static String acceptedValues() {
        return Arrays.stream(Role.values()).map(Role::toString).collect(Collectors.joining(", "));
    }

}
