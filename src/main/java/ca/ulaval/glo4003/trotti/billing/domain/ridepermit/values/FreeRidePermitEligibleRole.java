package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values;

import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum FreeRidePermitEligibleRole {
    TECHNICIAN, EMPLOYEE;

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
