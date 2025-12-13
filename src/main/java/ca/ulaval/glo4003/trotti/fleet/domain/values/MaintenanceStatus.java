package ca.ulaval.glo4003.trotti.fleet.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.StationMaintenanceException;

public class MaintenanceStatus {
    private final boolean active;
    private final Idul technicianId;

    private MaintenanceStatus(boolean active, Idul technicianId) {
        this.active = active;
        this.technicianId = technicianId;
    }

    public static MaintenanceStatus endMaintenance() {
        return new MaintenanceStatus(false, null);
    }

    public static MaintenanceStatus underMaintenance(Idul techId) {
        if (techId == null) {
            throw new StationMaintenanceException("Technician ID is required to start maintenance");
        }

        return new MaintenanceStatus(true, techId);
    }

    public boolean isStartedBy(Idul techId) {
        return active && technicianId != null && technicianId.equals(techId);
    }

    public boolean isActive() {
        return active;
    }

    public Idul getTechnicianId() {
        return technicianId;
    }
}
