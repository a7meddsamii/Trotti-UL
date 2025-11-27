package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StationFactory {

    public Station create(Location location, int capacity) {
        Map<SlotNumber, Optional<ScooterId>> slots = new HashMap<>();
        for (int i = 0; i < capacity; i++) {
            slots.put(new SlotNumber(i), Optional.empty());
        }
        return new Station(location, new DockingArea(slots));
    }
}
