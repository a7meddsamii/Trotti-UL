package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.MaintenanceStatus;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.List;

public class Station {
    private static final double INITIAL_FILL_PERCENTAGE = 0.8;
    private final Location location;
    private final DockingArea dockingArea;
    private MaintenanceStatus maintenanceStatus;

    public Station(Location location, DockingArea dockingArea) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.maintenanceStatus = MaintenanceStatus.endMaintenance();
    }

    public Station(Location location, DockingArea dockingArea, MaintenanceStatus underMaintenance) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.maintenanceStatus = underMaintenance;
    }

    public Scooter takeScooter(SlotNumber slotNumber, LocalDateTime undockingTime) {
        if (this.maintenanceStatus.isActive()) {
            throw new StationMaintenanceException("Station is under maintenance");
        }

        Scooter undockedScooter = this.dockingArea.undock(slotNumber);
        undockedScooter.beginUsage(undockingTime);
        return undockedScooter;
    }

    public void parkScooter(SlotNumber slotNumber, Scooter scooter, LocalDateTime dockTime) {
        if (this.maintenanceStatus.isActive()) {
            throw new StationMaintenanceException("Station is under maintenance");
        }

        this.dockingArea.dock(slotNumber, scooter);
        scooter.endUsage(this.location, dockTime);
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

    public void startMaintenance(Idul technicianId, LocalDateTime startTimeOfMaintenance) {
        if (this.maintenanceStatus.isActive()) {
            throw new StationMaintenanceException("Station is already under maintenance");
        }

        dockingArea.turnOffElectricity(startTimeOfMaintenance);
        this.maintenanceStatus = MaintenanceStatus.underMaintenance(technicianId);
    }

    public void endMaintenance(Idul technicianId, LocalDateTime endTimeOfMaintenance) {
        if (!this.maintenanceStatus.isActive()) {
            throw new StationMaintenanceException("Station is not under maintenance");
        }

        if (!this.maintenanceStatus.isStartedBy(technicianId)) {
            throw new StationMaintenanceException(
                    "Station maintenance can only be ended by the technician who started it");
        }

        dockingArea.turnOnElectricity(endTimeOfMaintenance);
        this.maintenanceStatus = MaintenanceStatus.endMaintenance();
    }

    public MaintenanceStatus getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public List<SlotNumber> getOccupiedSlots() {
        return dockingArea.findOccupiedSlots();
    }

    public List<SlotNumber> getAvailableSlots() {
        return dockingArea.findAvailableSlots();
    }

    public List<Scooter> retrieveScootersForTransfer(List<SlotNumber> slotNumbers) {
        return slotNumbers.stream().map(this.dockingArea::undock).toList();
    }

    public void parkScooters(List<SlotNumber> slotNumbers, List<Scooter> scooters,
            LocalDateTime dockTime) {
        for (int i = 0; i < slotNumbers.size(); i++) {
            SlotNumber slotNumber = slotNumbers.get(i);
            Scooter scooter = scooters.get(i);
            parkScooter(slotNumber, scooter, dockTime);
        }
    }

    public void ensureNotUnderMaintenance() {
        if (maintenanceStatus.isActive()) {
            throw new StationMaintenanceException("Station is under maintenance");
        }
    }
}
