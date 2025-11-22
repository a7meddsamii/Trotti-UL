package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.trip.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DockingArea {
    private final Map<SlotNumber, Optional<ScooterId>> scooterSlots;

    public DockingArea(Map<SlotNumber, Optional<ScooterId>> scooterSlots) {
        this.scooterSlots = scooterSlots;
    }

    public void dock(SlotNumber slotNumber, ScooterId scooterId) {
        validateSlotNumber(slotNumber);
        if (scooterSlots.get(slotNumber).isPresent()) {
            throw new DockingException("Slot " + slotNumber + " is occupied.");
        }
        scooterSlots.put(slotNumber, Optional.of(scooterId));
    }

    public ScooterId undock(SlotNumber slotNumber) {
        validateSlotNumber(slotNumber);
        Optional<ScooterId> scooterId = scooterSlots.get(slotNumber);
        if (scooterId.isEmpty()) {
            throw new DockingException("Slot " + slotNumber + " does not hold a scooter.");
        }
        scooterSlots.put(slotNumber, Optional.empty());
        return scooterId.get();
    }

    public int getCapacity() {
        return scooterSlots.size();
    }

    private void validateSlotNumber(SlotNumber slotNumber) {
        if (!scooterSlots.containsKey(slotNumber)) {
            throw new DockingException("Slot " + slotNumber + " does not exist at this station.");
        }
    }

    public Map<SlotNumber, Optional<ScooterId>> getScooterSlots() {
        return Map.copyOf(scooterSlots);
    }

    public List<SlotNumber> findOccupiedSlots() {
        return scooterSlots.entrySet().stream().filter(entry -> entry.getValue().isPresent())
                .map(Map.Entry::getKey).toList();
    }

    public List<SlotNumber> findAvailableSlots() {
        return scooterSlots.entrySet().stream().filter(entry -> entry.getValue().isEmpty())
                .map(Map.Entry::getKey).toList();
    }

    public List<ScooterId> getAllScooterIds() {
        return scooterSlots.values().stream().flatMap(Optional::stream).toList();
    }

}
