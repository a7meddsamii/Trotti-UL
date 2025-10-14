package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

import java.time.LocalDateTime;

public class Scooter {
    private final Id id;
    private BatteryLevel batteryLevel;
    private BatteryState currentBatteryState;
    private LocalDateTime lastBatteryUpdate;
	private Location stationLocation;

    public Scooter(
            Id id,
            BatteryLevel batteryLevel,
            LocalDateTime lastBatteryUpdate,
            BatteryState currentBatteryState,
			Location stationLocation
	) {
        this.id = id;
        this.batteryLevel = batteryLevel;
        this.lastBatteryUpdate = lastBatteryUpdate;
        this.currentBatteryState = currentBatteryState;
		this.stationLocation = stationLocation;
    }

    private void changeBatteryState(BatteryState newState, LocalDateTime dateTimeOfChange) {
        if (dateTimeOfChange.isBefore(lastBatteryUpdate)) {
            throw new InvalidBatteryUpdate(
                    "The date of the battery state change cannot be before the last update.");
        }

        if (newState == currentBatteryState) {
            return;
        }

        this.batteryLevel = this.currentBatteryState.computeLevel(batteryLevel, lastBatteryUpdate, dateTimeOfChange);
        this.lastBatteryUpdate = dateTimeOfChange;
        this.currentBatteryState = newState;
    }
	
	public void dockAt(Location location, LocalDateTime dockingTime){
		if (!this.stationLocation.isEmpty()) {
			throw new InvalidLocation("scooter seems to be already docked at " + this.stationLocation);
		}
		
		this.stationLocation = location;
		changeBatteryState(BatteryState.CHARGING, dockingTime);
	}
	
	public void undock(LocalDateTime undockingTime) {
		if (this.stationLocation.isEmpty()) {
			throw new InvalidLocation("scooter seems to already be undocked");
		}
		
		this.stationLocation = Location.empty();
		changeBatteryState(BatteryState.DISCHARGING, undockingTime);
	}

    public BatteryLevel getBatteryLevel() {
        return batteryLevel;
    }

    public LocalDateTime getLastBatteryUpdate() {
        return lastBatteryUpdate;
    }
	
	public Location getStationLocation() {
		return stationLocation;
	}
}
