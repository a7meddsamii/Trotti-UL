package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.MaintenanceStatus;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Station(
            Location location,
            DockingArea dockingArea,
			MaintenanceStatus underMaintenance) {
        this.location = location;
        this.dockingArea = dockingArea;
        this.maintenanceStatus = underMaintenance;
    }

    public Scooter getScooter(SlotNumber slotNumber, LocalDateTime undockingTime) {
        if(this.maintenanceStatus.isActive()) {
			throw new StationMaintenanceException("Station is under maintenance");
		}
		
        Scooter undockedScooter = this.dockingArea.undock(slotNumber);
		undockedScooter.beginUsage(undockingTime);
		return undockedScooter;
    }

    public void returnScooter(SlotNumber slotNumber, Scooter scooter, LocalDateTime dockTime) {
		if(this.maintenanceStatus.isActive()) {
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

    public void startMaintenance(Idul technicianId) {
		if(this.maintenanceStatus.isActive()) {
			throw new StationMaintenanceException("Station is already under maintenance");
		}
		
        this.maintenanceStatus = MaintenanceStatus.underMaintenance(technicianId);
    }

    public void endMaintenance(Idul technicianId) {
		if(!this.maintenanceStatus.isActive()) {
			throw new StationMaintenanceException("Station is not under maintenance");
		}
		
		if(!this.maintenanceStatus.startedBy(technicianId)) {
			throw new StationMaintenanceException("Station maintenance can only be ended by the technician who started it");
		}
		
		this.maintenanceStatus = MaintenanceStatus.endMaintenance();
    }

    public List<SlotNumber> getOccupiedSlots() {
        return dockingArea.findOccupiedSlots();
    }

    public List<SlotNumber> getAvailableSlots() {
        return dockingArea.findAvailableSlots();
    }

    public Set<Scooter> retrieveScootersForTransfer(List<SlotNumber> slotNumbers) {
        return slotNumbers.stream().map(this.dockingArea::undock).collect(Collectors.toSet());
    }

    public void returnScooters(Map<SlotNumber, Scooter> scooters, LocalDateTime dockTime) {
        scooters.forEach((slotNumber, scooter) -> returnScooter(slotNumber, scooter, dockTime));
    }
}
