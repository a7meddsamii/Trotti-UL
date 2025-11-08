package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.values.permissions.*;

import java.util.Set;

public enum Role {
    ETUDIANT {
        public Set<Permission> permissions() {
            return Set.of(
                    TripPermissions.MAKE_TRIP,
                    CartPermissions.CART_MODIFICATION,
                    MaintenancePermissions.REQUEST_MAINTENANCE,
                    OrderPermissions.ORDER_CONFIRM
            );
        }
    },
    EMPLOYE {
        public Set<Permission> permissions() {
            return Set.of(
                    TripPermissions.MAKE_TRIP,
                    MaintenancePermissions.REQUEST_MAINTENANCE
            );
        }
    },
    TECHNICIEN {
        public Set<Permission> permissions() {
            return Set.of(
                    TripPermissions.MAKE_TRIP,
                    MaintenancePermissions.START_MAINTENANCE,
                    MaintenancePermissions.END_MAINTENANCE,
                    MaintenancePermissions.RELOCATE_SCOOTER,
                    MaintenancePermissions.REQUEST_MAINTENANCE
            );
        }
    };
    
    public abstract Set<Permission> permissions();
}