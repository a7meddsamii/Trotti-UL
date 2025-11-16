package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Role {

    STUDENT, EMPLOYEE, TECHNICIAN, ADMIN;


    public static Role fromString(String value) {
        String normalizedValue = value.trim().toUpperCase();
        try {
            return Role.valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(
                    "Invalid role: " + value + ". Accepted values are: " + acceptedValues());
        }
    }

    private static String acceptedValues() {
        return Arrays.stream(Role.values()).map(Role::name).collect(Collectors.joining(", "));
    }

}
