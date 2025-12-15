package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidStationOperation;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.util.HashMap;
import java.util.Map;

public class StationFactory {

    public Station create(Location location, int capacity) {
        validateCapacity(capacity);

        Map<SlotNumber, ScooterSlot> emptySlots = buildEmptySlots(capacity);
        DockingArea dockingArea = new DockingArea(emptySlots);

        return new Station(location, dockingArea);
    }

    private void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new InvalidStationOperation("Station capacity must be greater than zero");
        }
    }

    private Map<SlotNumber, ScooterSlot> buildEmptySlots(int capacity) {
        Map<SlotNumber, ScooterSlot> scooterSlots = new HashMap<>();

        for (int i = 0; i < capacity; i++) {
            SlotNumber slotNumber = SlotNumber.from(i);
            scooterSlots.put(slotNumber, new ScooterSlot(slotNumber));
        }

        return scooterSlots;
    }
}
