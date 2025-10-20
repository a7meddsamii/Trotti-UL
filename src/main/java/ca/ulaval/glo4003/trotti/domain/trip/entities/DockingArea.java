package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;

import java.util.Map;

public class DockingArea {
	private final Map<Integer, ScooterSlot> scooterSlots;
	
	public DockingArea(Map<Integer, ScooterSlot> scooterSlots) {
		this.scooterSlots = scooterSlots;
	}
	
	public void dock(int slotNumber, Id scooterId) {
		validateSlotNumber(slotNumber);
		if (scooterSlots.get(slotNumber).isOccupied()) {
			throw new DockingException("Slot " + slotNumber + " is occupied. Available slots: " + availableSlots());
		}
		
		scooterSlots.get(slotNumber).dock(scooterId);
	}
	
	public Id undock(int slotNumber) {
		validateSlotNumber(slotNumber);
		if (!scooterSlots.get(slotNumber).isOccupied()) {
			throw new DockingException("Slot " + slotNumber + " does not hold a scooter.");
		}
		
		Id scooterId = scooterSlots.get(slotNumber).getScooterId().orElseThrow();
		scooterSlots.get(slotNumber).undock();
		return scooterId;
	}
	
	private void validateSlotNumber(int slotNumber) {
		if (!scooterSlots.containsKey(slotNumber)) {
			throw new DockingException("Slot " + slotNumber + " does not exist at this station.");
		}
	}
	
	// Louis don't test this, it's meant for error messages only TODO you can delete this comment once you've read it
	private String availableSlots() {
		return scooterSlots.keySet().stream()
				.filter(slotNumber -> !scooterSlots.get(slotNumber).isOccupied())
				.map(Object::toString)
				.reduce((a, b) -> a + ", " + b)
				.orElse("no more slots available");
	}
}
