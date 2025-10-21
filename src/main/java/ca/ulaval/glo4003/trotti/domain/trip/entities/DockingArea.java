package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.Map;

public class DockingArea {
    private final Map<Integer, ScooterSlot> scooterSlots;

    public DockingArea(Map<Integer, ScooterSlot> scooterSlots) {
        this.scooterSlots = scooterSlots;
    }

    public void dock(int slotNumber, ScooterId scooterId) {
        validateSlotNumber(slotNumber);
        scooterSlots.get(slotNumber).dock(scooterId);
    }

    public ScooterId undock(int slotNumber) {
        validateSlotNumber(slotNumber);
        return scooterSlots.get(slotNumber).undock();
    }

    private void validateSlotNumber(int slotNumber) {
        if (!scooterSlots.containsKey(slotNumber)) {
            throw new DockingException("Slot " + slotNumber + " does not exist at this station.");
        }
    }
}
