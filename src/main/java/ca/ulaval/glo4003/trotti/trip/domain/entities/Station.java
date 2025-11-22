package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Station {

    private static final double INITIAL_FILL_PERCENTAGE = 0.8;

    private final Location location;
    private final DockingArea dockingArea;
    private boolean underMaintenance;
    private Idul technicianId;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.underMaintenance = false;
        this.technicianId = null;
    }

    public ScooterId getScooter(SlotNumber slotNumber) {
        if (underMaintenance) {
            throw new StationMaintenanceException(
                    "Cannot get scooter from station under maintenance");
        }
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(SlotNumber slotNumber, ScooterId scooterId) {
        if (underMaintenance) {
            throw new StationMaintenanceException(
                    "Cannot return scooter to station under maintenance");
        }
        this.dockingArea.dock(slotNumber, scooterId);
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

    public void startMaintenance(Idul technicianId) {
        if (this.underMaintenance) {
            throw new StationMaintenanceException("Station is already under maintenance");
        }
        this.underMaintenance = true;
        this.technicianId = technicianId;
    }

    public void endMaintenance(Idul technicianId) {
        if (!this.underMaintenance) {
            throw new StationMaintenanceException("Station is not under maintenance");
        }
        if (!this.technicianId.equals(technicianId)) {
            throw new StationMaintenanceException(
                    "Only the technician who started the maintenance can end it");
        }
        this.underMaintenance = false;
        this.technicianId = null;
    }

    public List<SlotNumber> getOccupiedSlots() {
        return dockingArea.findOccupiedSlots();
    }

    public List<SlotNumber> getAvailableSlots() {
        return dockingArea.findAvailableSlots();
    }

    public Set<ScooterId> retrieveScootersForTransfer(List<SlotNumber> slotNumbers) {
        return slotNumbers.stream().map(this.dockingArea::undock).collect(Collectors.toSet());
    }

    public List<ScooterId> getAllScooterIds() {
        return dockingArea.getAllScooterIds();
    }
}
