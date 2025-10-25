package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.Map;

public class DockingArea {
    private final Map<SlotNumber, ScooterSlot> scooterSlots;

    public DockingArea(Map<SlotNumber, ScooterSlot> scooterSlots) {
        this.scooterSlots = scooterSlots;
    }

    public void dock(SlotNumber slotNumber, ScooterId scooterId) {
        validateSlotNumber(slotNumber);
        scooterSlots.get(slotNumber).dock(scooterId);
    }

    public ScooterId undock(SlotNumber slotNumber) {
        validateSlotNumber(slotNumber);
        return scooterSlots.get(slotNumber).undock();
    }

    public int getCapacity() {
        return scooterSlots.size();
    }

    private void validateSlotNumber(SlotNumber slotNumber) {
        if (!scooterSlots.containsKey(slotNumber)) {
            throw new DockingException("Slot " + slotNumber + " does not exist at this station.");
        }
    }
}
