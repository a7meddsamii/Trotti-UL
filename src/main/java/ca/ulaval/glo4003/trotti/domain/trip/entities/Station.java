package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.List;

public class Station {
    private final Location location;
    private final List<ScooterSlot> scooterSlots;

    public Station(Location location, List<ScooterSlot> scooterSlots) {
        this.location = location;
        this.scooterSlots = scooterSlots;
    }

    public void dockScooter(Id scooterId) {
        if (this.contains(scooterId)) {
            throw new DockingException("Scooter is already in this station");
        }
        if (this.atCapacity()) {
            throw new DockingException("Station is at capacity");
        }

        for (ScooterSlot scooterSlot : scooterSlots) {
            if (!scooterSlot.isOccupied()) {
                scooterSlot.dock(scooterId);
                break;
            }
        }
    }

    public void undockScooter(Id scooterId) {
        if (!this.contains(scooterId)) {
            throw new DockingException("Scooter is not in this station");
        }

        for (ScooterSlot scooterSlot : scooterSlots) {
            if (scooterSlot.isOccupied()
                    && scooterSlot.getScooterId().filter(id -> id.equals(scooterId)).isPresent()) {
                scooterSlot.undock();
            }
        }
    }

    private boolean contains(Id scooterId) {
        return scooterSlots.stream().anyMatch(
                slot -> slot.getScooterId().filter(id -> id.equals(scooterId)).isPresent());
    }

    private boolean atCapacity() {
        return scooterSlots.stream().allMatch(ScooterSlot::isOccupied);
    }
}
