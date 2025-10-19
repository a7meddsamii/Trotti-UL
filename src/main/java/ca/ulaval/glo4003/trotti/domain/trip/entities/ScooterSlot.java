package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.util.Optional;

public class ScooterSlot {
    private Id dockedScooter;

    public Optional<Id> getScooterId() {
        return Optional.ofNullable(dockedScooter);
    }

    public boolean isOccupied() {
        return getScooterId().isPresent();
    }

    public void dock(Id scooterId) {
        this.dockedScooter = scooterId;
    }

    public void undock() {
        this.dockedScooter = null;
    }
}
