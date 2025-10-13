package ca.ulaval.glo4003.trotti.domain.trip.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;

import java.time.LocalDateTime;

public class Scooter {
    private final Id id;
	private Battery battery;
	private BatteryState currentBatteryState;
	private LocalDateTime lastBatteryUpdate;

    public Scooter(
            Id id,
			Battery battery,
			LocalDateTime lastBatteryUpdate, BatteryState currentBatteryState) {
        this.id = id;
        this.battery = battery;
        this.lastBatteryUpdate = lastBatteryUpdate;
		this.currentBatteryState = currentBatteryState;
    }
	
	public void updateBatteryState(BatteryState newState, LocalDateTime dateTimeOfChange) {
		if (dateTimeOfChange.isBefore(lastBatteryUpdate)) {
			throw new InvalidBatteryUpdate("The date of the battery state change cannot be before the last update.");
		}
		
		if (newState == currentBatteryState) {
			return;
		}
		
		this.battery = newState.computeLevel(battery, lastBatteryUpdate, dateTimeOfChange);
		this.lastBatteryUpdate = dateTimeOfChange;
		this.currentBatteryState = newState;
	}
	
	public Battery getBattery() {
		return battery;
	}
	
	public LocalDateTime getLastBatteryUpdate() {
		return lastBatteryUpdate;
	}
}
