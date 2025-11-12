package ca.ulaval.glo4003.trotti.account.domain.values.permissions;

import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import java.util.Map;
import java.util.Set;

public final class RolePermissionsRegistry {
     private final Map<Role, Set<Permission>> rolePermissions = Map.of(
            Role.STUDENT, Set.of(
                    CartPermissions.CART_MODIFICATION,
                    OrderPermissions.ORDER_CONFIRM,
                    TripPermissions.MAKE_TRIP,
                    MaintenancePermissions.REQUEST_MAINTENANCE
            ),
            Role.EMPLOYEE, Set.of(
                    TripPermissions.MAKE_TRIP,
                    MaintenancePermissions.REQUEST_MAINTENANCE
                    
            ),
            Role.TECHNICIAN, Set.of(
                    TripPermissions.MAKE_TRIP,
                    MaintenancePermissions.START_MAINTENANCE,
                    MaintenancePermissions.END_MAINTENANCE,
                    MaintenancePermissions.RELOCATE_SCOOTER,
                    MaintenancePermissions.REQUEST_MAINTENANCE
            )
    );
    
    public Set<Permission> get(Role role) {
        return rolePermissions.getOrDefault(role, Set.of());
    }
}