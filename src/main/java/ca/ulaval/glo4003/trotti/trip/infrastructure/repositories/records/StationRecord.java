package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.Map;

public record StationRecord(Location location, Map<SlotNumber, ScooterId> slots, boolean underMaintenance, Idul technicianId) {
}
