package ca.ulaval.glo4003.trotti.identityAccess.domain.values;

public enum Role {
    STUDENT,
    REGULAR_EMPLOYEE,
    TECHNICIAN;

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
