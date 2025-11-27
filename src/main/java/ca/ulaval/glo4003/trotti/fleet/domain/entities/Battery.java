package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryState;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Battery {
    private static final BatteryLevel MINIMUM_OPERATIONAL_LEVEL =
            BatteryLevel.from(BigDecimal.valueOf(15));
    private BatteryLevel batteryLevel;
    private BatteryState currentBatteryState;
    private LocalDateTime lastBatteryUpdate;

    public Battery(
            BatteryLevel batteryLevel,
            LocalDateTime lastBatteryUpdate,
            BatteryState currentBatteryState) {
        this.batteryLevel = batteryLevel;
        this.lastBatteryUpdate = lastBatteryUpdate;
        this.currentBatteryState = currentBatteryState;
    }

    public boolean hasEnoughCharge() {
        return batteryLevel.isGreaterThan(MINIMUM_OPERATIONAL_LEVEL);
    }

    public void changeBatteryState(BatteryState newState, LocalDateTime dateTimeOfChange) {
        if (dateTimeOfChange.isBefore(lastBatteryUpdate)) {
            throw new InvalidBatteryUpdate(
                    "The date of the battery state change cannot be before the last update.");
        }

        if (newState == currentBatteryState) {
            return;
        }

        this.batteryLevel = this.currentBatteryState.computeLevel(lastBatteryUpdate,
                dateTimeOfChange, batteryLevel);
        this.lastBatteryUpdate = dateTimeOfChange;
        this.currentBatteryState = newState;
    }

    public BatteryLevel getBatteryLevel() {
        return batteryLevel;
    }

    public BatteryState getCurrentBatteryState() {
        return currentBatteryState;
    }

    public LocalDateTime getLastBatteryUpdate() {
        return lastBatteryUpdate;
    }

    public void pauseCharging(LocalDateTime pausedTime) {
        if (currentBatteryState == BatteryState.CHARGING) {
            changeBatteryState(BatteryState.IDLE, pausedTime);
        }
    }

    public void resumeCharging(LocalDateTime resumedTime) {
        if (currentBatteryState == BatteryState.IDLE) {
            changeBatteryState(BatteryState.CHARGING, resumedTime);
        }
    }
}
