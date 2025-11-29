package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

import java.util.Optional;

public class ScooterSlot {
    private final SlotNumber slotNumber;
    private Scooter dockedScooter;

    public ScooterSlot(SlotNumber slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ScooterSlot(SlotNumber slotNumber, Scooter scooter) {
        this.dockedScooter = scooter;
        this.slotNumber = slotNumber;
    }

    public void dock(Scooter scooter) {
        if (isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " is occupied.");
        }

        this.dockedScooter = scooter;
    }

    public Scooter undock() {
        if (!isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " does not hold a scooter.");
        }
		
		Scooter unDockedScooter = this.dockedScooter;
        this.dockedScooter = null;
        return unDockedScooter;
    }

    public Scooter getDockedScooter() {
        return dockedScooter;
    }

    public boolean isOccupied() {
        return Optional.ofNullable(dockedScooter).isPresent();
    }
}
