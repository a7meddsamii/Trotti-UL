package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;

public enum Permission {
    ORDER_CONFIRM, CART_MODIFICATION, MAKE_TRIP, START_MAINTENANCE, END_MAINTENANCE, TRANSFER_SCOOTER, REQUEST_MAINTENANCE, CREATE_TECHNICIAN, DELETE_TECHNICIAN;

    public static Permission fromString(String value) {
        for (Permission permission : Permission.values()) {
            if (permission.name().equalsIgnoreCase(value)) {
                return permission;
            }
        }

        throw new AuthenticationException("Unknown permission: " + value);
    }
}
