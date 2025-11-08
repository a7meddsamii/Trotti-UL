package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.values.permissions.*;
import java.util.Set;

public enum Role {

    USER(Set.of(CartPermissions.CART_MODIFICATION, OrderPermissions.ORDER_CONFIRM,
            TripPermissions.MAKE_TRIP, MaintenancePermissions.REQUEST_MAINTENANCE)), EMPLOYEE(
                    Set.of(TripPermissions.MAKE_TRIP,
                            MaintenancePermissions.REQUEST_MAINTENANCE)), TECHNICIAN(
                                    Set.of(TripPermissions.MAKE_TRIP,
                                            MaintenancePermissions.START_MAINTENANCE,
                                            MaintenancePermissions.END_MAINTENANCE,
                                            MaintenancePermissions.RELOCATE_SCOOTER,
                                            MaintenancePermissions.REQUEST_MAINTENANCE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = Set.copyOf(permissions);
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

}
