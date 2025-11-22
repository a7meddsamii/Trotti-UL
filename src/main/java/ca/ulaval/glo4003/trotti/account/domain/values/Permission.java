package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;

public enum Permission {
    ORDER_CONFIRM, CART_MODIFICATION, MAKE_TRIP, START_MAINTENANCE, END_MAINTENANCE, RELOCATE_SCOOTERS, REQUEST_MAINTENANCE, CREATE_EMPLOYEE, DELETE_EMPLOYEE, CREATE_ADMIN, DELETE_ADMIN;

    public static Permission fromString(String value) {
        for (Permission permission : Permission.values()) {
            if (permission.name().equalsIgnoreCase(value)) {
                return permission;
            }
        }

        throw new AuthenticationException("Unknown permission: " + value);
    }
}
