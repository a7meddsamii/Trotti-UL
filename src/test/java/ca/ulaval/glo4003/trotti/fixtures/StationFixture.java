package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.trip.domain.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.HashMap;
import java.util.Map;

public class StationFixture {
    public static final Location A_LOCATION = Location.of("vachon", "stationX");
    private final Map<SlotNumber, ScooterSlot> scooterSlots = new HashMap<>();
    private Location location = A_LOCATION;

    public StationFixture withEmptySlot(SlotNumber slotNumber) {
        scooterSlots.put(slotNumber, new ScooterSlot(slotNumber));
        return this;
    }

    public StationFixture withOccupiedSlot(SlotNumber slotNumber, ScooterId scooterId) {
        ScooterSlot slot = new ScooterSlot(slotNumber);
        slot.dock(scooterId);
        scooterSlots.put(slotNumber, slot);
        return this;
    }

    public StationFixture withLocation(Location location) {
        this.location = location;
        return this;
    }

    public Station build() {
        DockingArea dockingArea = new DockingArea(scooterSlots);
        return new Station(location, dockingArea);
    }

}
