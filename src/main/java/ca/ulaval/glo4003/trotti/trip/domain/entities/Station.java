package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
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

    public Station(
            Location location,
            DockingArea dockingArea,
            boolean underMaintenance,
            Idul technicianId) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.underMaintenance = underMaintenance;
        this.technicianId = technicianId;
    }

    public ScooterId getScooter(SlotNumber slotNumber) {
        validateNotUnderMaintenance();
        return this.dockingArea.undock(slotNumber);
    }

    public void returnScooter(SlotNumber slotNumber, ScooterId scooterId) {
        validateNotUnderMaintenance();
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
        validateTechnicianInCharge(technicianId);
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

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public Idul getTechnicianId() {
        return technicianId;
    }

    public void validateTechnicianInCharge(Idul technicianId) {
        validateUnderMaintenance();
        if (!this.technicianId.equals(technicianId)) {
            throw new StationMaintenanceException(
                    "Only the technician in charge of station maintenance can perform this operation");
        }
    }

    public void validateNotUnderMaintenance() {
        if (underMaintenance) {
            throw new StationMaintenanceException(
                    "Cannot perform operation on station under maintenance");
        }
    }

    private void validateUnderMaintenance() {
        if (!underMaintenance) {
            throw new StationMaintenanceException("Station is not under maintenance");
        }
    }

    public void returnScooters(List<SlotNumber> slots, List<ScooterId> scooterIds) {
        for (int i = 0; i < scooterIds.size(); i++) {
            returnScooter(slots.get(i), scooterIds.get(i));
        }
    }
}
