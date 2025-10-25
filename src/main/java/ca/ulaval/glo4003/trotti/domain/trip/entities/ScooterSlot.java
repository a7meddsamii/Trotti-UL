package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.Optional;

public class ScooterSlot {
    private final SlotNumber slotNumber;
    private ScooterId dockedScooter;

    public ScooterSlot(SlotNumber slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ScooterSlot(SlotNumber slotNumber, ScooterId dockedScooter) {
        this.dockedScooter = dockedScooter;
        this.slotNumber = slotNumber;
    }

    public void dock(ScooterId scooterId) {
        if (isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " is occupied.");
        }

        this.dockedScooter = scooterId;
    }

    public ScooterId undock() {
        if (!isOccupied()) {
            throw new DockingException("Slot " + slotNumber + " does not hold a scooter.");
        }

        ScooterId unDockedScooter = this.dockedScooter;
        this.dockedScooter = null;
        return unDockedScooter;
    }

    public boolean containsScooterId(ScooterId scooterId) {
        return Optional.ofNullable(dockedScooter).map(docked -> docked.equals(scooterId))
                .orElse(false);
    }

    public Optional<ScooterId> getDockedScooter() {
        return Optional.ofNullable(dockedScooter);
    }

    private boolean isOccupied() {
        return Optional.ofNullable(dockedScooter).isPresent();
    }

}
