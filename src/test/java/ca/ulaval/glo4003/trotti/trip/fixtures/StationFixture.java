package ca.ulaval.glo4003.trotti.trip.fixtures;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @deprecated This class will be reimplemented
 */
public class StationFixture {
    private final Map<SlotNumber, Optional<ScooterId>> scooterSlots = new HashMap<>();
    private Location location = Location.of("vachon", "stationX");
    private boolean isUnderMaintenance = false;
    private Idul technicianId = Idul.from("tech123");

    public StationFixture withEmptySlot(SlotNumber slotNumber) {
        scooterSlots.put(slotNumber, Optional.empty());
        return this;
    }

    public StationFixture withOccupiedSlot(SlotNumber slotNumber, ScooterId scooterId) {
        scooterSlots.put(slotNumber, Optional.of(scooterId));
        return this;
    }

    public StationFixture withLocation(Location location) {
        this.location = location;
        return this;
    }

    public Station build() {
        // DockingArea dockingArea = new DockingArea(scooterSlots);
        return new Station(location, null);
    }
}
