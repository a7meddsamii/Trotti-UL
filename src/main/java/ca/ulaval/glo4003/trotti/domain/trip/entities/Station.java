package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

public class Station {
    private final Location location;
    private final DockingArea dockingArea;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
    }

    public Id getScooter(int slotNumber) {
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(int slotNumber, Id scooterId) {
        this.dockingArea.dock(slotNumber, scooterId);
    }
}
