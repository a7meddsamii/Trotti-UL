package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;

public class Station {

    private static final double INITIAL_FILL_PERCENTAGE = 0.8;

    private final Location location;
    private final DockingArea dockingArea;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
    }

    public ScooterId getScooter(SlotNumber slotNumber) {
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(SlotNumber slotNumber, Scooter scooter, LocalDateTime time) {
        scooter.dockAt(this.location, time);
        this.dockingArea.dock(slotNumber, scooter.getScooterId());
    }

    public Location getLocation() {
        return location;
    }

    public DockingArea getDockingArea() {
        return dockingArea;
    }

    public int calculateInitialScooterCount() {
        return (int) Math.round(dockingArea.getCapacity() * INITIAL_FILL_PERCENTAGE);
    }
}
