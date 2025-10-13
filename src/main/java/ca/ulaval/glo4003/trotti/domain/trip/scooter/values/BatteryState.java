package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.BatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.ChargeBatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.DischargeBatteryStrategy;

import java.time.LocalDateTime;

public enum BatteryState {
	DISCHARGING(DischargeBatteryStrategy.INSTANCE), CHARGING(ChargeBatteryStrategy.INSTANCE);
	
	private final BatteryStrategy batteryStrategy;
	
	BatteryState(BatteryStrategy batteryStrategy) {
		this.batteryStrategy = batteryStrategy;
	}
	
	public Battery computeLevel(Battery battery, 
								  LocalDateTime lastBatteryUpdate, 
								  LocalDateTime currentTime) {
		return batteryStrategy.computeLevel(lastBatteryUpdate, currentTime, battery);
	}
}
