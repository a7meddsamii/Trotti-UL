package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;

public class Station {

    private static final double INITIAL_FILL_PERCENTAGE = 0.8;

    private final Location location;
    private final DockingArea dockingArea;
    private boolean underMaintenance;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.underMaintenance = false;
    }

    public ScooterId getScooter(SlotNumber slotNumber) {
        if (underMaintenance) {
            throw new StationMaintenanceException("Cannot get scooter from station under maintenance");
        }
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(SlotNumber slotNumber, Scooter scooter, LocalDateTime time) {
        if (underMaintenance) {
            throw new StationMaintenanceException("Cannot return scooter to station under maintenance");
        }
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

    public void startMaintenance() {
        this.underMaintenance = true;
    }

    public void endMaintenance() {
        this.underMaintenance = false;
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public java.util.List<ScooterId> getDockedScooterIds() {
        return dockingArea.getScooterSlots().values().stream()
                .map(slot -> slot.getDockedScooter().orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();
    }
}
