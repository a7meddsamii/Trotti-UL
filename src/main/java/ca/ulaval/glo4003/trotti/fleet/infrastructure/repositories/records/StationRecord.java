package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.MaintenanceStatus;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.util.Map;

public record StationRecord(Location location, Map<SlotNumber, ScooterRecord> dockingAreaRecord, MaintenanceStatus maintenanceStatus) {
}
