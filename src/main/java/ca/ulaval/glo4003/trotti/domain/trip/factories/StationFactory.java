package ca.ulaval.glo4003.trotti.domain.trip.factories;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.DockingArea;
import ca.ulaval.glo4003.trotti.domain.trip.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.HashMap;
import java.util.Map;

public class StationFactory {

    public Station create(Location location, int capacity) {
        DockingArea emptyDockingArea = createEmptyDockingArea(capacity);
        return new Station(location, emptyDockingArea);
    }

    private DockingArea createEmptyDockingArea(int capacity) {
        Map<SlotNumber, ScooterSlot> scooterSlots = new HashMap<>();
        for (int i = 0; i < capacity; i++) {
            SlotNumber slotNumber = new SlotNumber(i);
            scooterSlots.put(slotNumber, new ScooterSlot(slotNumber));
        }
        return new DockingArea(scooterSlots);
    }
}
