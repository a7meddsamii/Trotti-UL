package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.HashMap;

public class Station {
    private final Location location;
    private final DockingArea dockingArea;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
    }

    public ScooterId getScooter(SlotNumber slotNumber) {
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(SlotNumber slotNumber, ScooterId scooterId) {
        this.dockingArea.dock(slotNumber, scooterId);
    }

    public Location getLocation() {
        return location;
    }

    public DockingArea getDockingArea() {
        return dockingArea;
    }
}
