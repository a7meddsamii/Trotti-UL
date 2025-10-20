package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import java.util.Optional;

public class ScooterSlot {
    private final int slotNumber;
    private Id dockedScooter;

    public ScooterSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void dock(Id scooterId) {
        if (isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " is occupied.");
        }

        this.dockedScooter = scooterId;
    }

    public Id undock() {
        if (!isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " does not hold a scooter.");
        }

        Id unDockedScooter = this.dockedScooter;
        this.dockedScooter = null;
        return unDockedScooter;
    }

    private boolean isOccupied() {
        return Optional.ofNullable(dockedScooter).isPresent();
    }
}
