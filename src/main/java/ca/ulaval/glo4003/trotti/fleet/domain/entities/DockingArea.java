package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DockingArea {
    private final Map<SlotNumber, ScooterSlot> scooterSlots;

    public DockingArea(Map<SlotNumber, ScooterSlot> scooterSlots) {
        this.scooterSlots = scooterSlots;
    }

    public void dock(SlotNumber slotNumber, Scooter scooter) {
        validateSlotNumber(slotNumber);
        ScooterSlot scooterSlot = scooterSlots.get(slotNumber);
        scooterSlot.dock(scooter);
    }

    public Scooter undock(SlotNumber slotNumber) {
        validateSlotNumber(slotNumber);
        ScooterSlot scooterSlot = scooterSlots.get(slotNumber);
        return scooterSlot.undock();
    }

    public void turnOffElectricity(LocalDateTime switchOffTime) {
        getDockedScooters().forEach((slotNumber, scooter) -> scooter.pauseCharging(switchOffTime));
    }

    public void turnOnElectricity(LocalDateTime switchOnTime) {
        getDockedScooters().forEach((slotNumber, scooter) -> scooter.resumeCharging(switchOnTime));
    }

    public int getCapacity() {
        return scooterSlots.size();
    }

    public Map<SlotNumber, ScooterSlot> getScooterSlots() {
        return Collections.unmodifiableMap(scooterSlots);
    }

    public List<SlotNumber> findOccupiedSlots() {
        return scooterSlots.entrySet().stream().filter(entry -> entry.getValue().isOccupied())
                .map(Map.Entry::getKey).toList();
    }

    public List<SlotNumber> findAvailableSlots() {
        return scooterSlots.entrySet().stream().filter(entry -> !entry.getValue().isOccupied())
                .map(Map.Entry::getKey).toList();
    }

    public Map<SlotNumber, Scooter> getDockedScooters() {
        Map<SlotNumber, Scooter> scooters = new HashMap<>();

        for (Map.Entry<SlotNumber, ScooterSlot> slotEntry : scooterSlots.entrySet()) {
            if (slotEntry.getValue().isOccupied()) {
                scooters.put(slotEntry.getKey(), slotEntry.getValue().getDockedScooter());
            }
        }

        return scooters;
    }

    private void validateSlotNumber(SlotNumber slotNumber) {
        if (!scooterSlots.containsKey(slotNumber)) {
            throw new DockingException("Slot " + slotNumber + " does not exist at this station.");
        }
    }
}
