package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import java.time.LocalDateTime;

public class Scooter {
    private final Id id;
    private BatteryLevel batteryLevel;
    private BatteryState currentBatteryState;
    private LocalDateTime lastBatteryUpdate;

    public Scooter(
            Id id,
            BatteryLevel batteryLevel,
            LocalDateTime lastBatteryUpdate,
            BatteryState currentBatteryState) {
        this.id = id;
        this.batteryLevel = batteryLevel;
        this.lastBatteryUpdate = lastBatteryUpdate;
        this.currentBatteryState = currentBatteryState;
    }

    public void updateBatteryState(BatteryState newState, LocalDateTime dateTimeOfChange) {
        if (dateTimeOfChange.isBefore(lastBatteryUpdate)) {
            throw new InvalidBatteryUpdate(
                    "The date of the battery state change cannot be before the last update.");
        }

        if (newState == currentBatteryState) {
            return;
        }

        this.batteryLevel =
                newState.computeLevel(batteryLevel, lastBatteryUpdate, dateTimeOfChange);
        this.lastBatteryUpdate = dateTimeOfChange;
        this.currentBatteryState = newState;
    }

    public BatteryLevel getBattery() {
        return batteryLevel;
    }

    public LocalDateTime getLastBatteryUpdate() {
        return lastBatteryUpdate;
    }
}
