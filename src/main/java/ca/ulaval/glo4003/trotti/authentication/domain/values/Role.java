package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;

public enum Role {
    STUDENT, REGULAR_EMPLOYEE, TECHNICIAN;

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }

        throw new AuthenticationException("Unknown role: " + value);
    }
}
