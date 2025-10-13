package ca.ulaval.glo4003.trotti.domain.trip.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.BatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;

import java.time.LocalDateTime;
import java.util.EnumMap;

public class Scooter {
    private final Id id;
	private final EnumMap<BatteryState, BatteryStrategy> batteryStrategies;
	private Battery battery;
	private BatteryState currentBatteryState;
	private LocalDateTime lastBatteryUpdate;

    public Scooter(
            Id id,
			Battery battery,
			EnumMap<BatteryState, BatteryStrategy> batteryStrategies,
			LocalDateTime lastBatteryUpdate, BatteryState currentBatteryState) {
        this.id = id;
        this.battery = battery;
        this.batteryStrategies = batteryStrategies;
        this.lastBatteryUpdate = lastBatteryUpdate;
		this.currentBatteryState = currentBatteryState;
    }
	
	public void updateBatteryState(BatteryState newState, LocalDateTime dateTimeOfChange) {
		updateBattery(this.currentBatteryState, dateTimeOfChange);
		this.lastBatteryUpdate = dateTimeOfChange;
		this.currentBatteryState = newState;
	}

    private void updateBattery(BatteryState batteryState, LocalDateTime currentTime) {
        this.battery = batteryStrategies.get(batteryState).computeLevel(lastBatteryUpdate,
																		currentTime, battery);
        this.lastBatteryUpdate = currentTime;
    }

    public Id getId() {
        return id;
    }
}
