package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.BatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.ChargingStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.DischargingStrategy;
import java.time.LocalDateTime;

public enum BatteryState {
    DISCHARGING(DischargingStrategy.INSTANCE), CHARGING(ChargingStrategy.INSTANCE);

    private final BatteryStrategy batteryStrategy;

    BatteryState(BatteryStrategy batteryStrategy) {
        this.batteryStrategy = batteryStrategy;
    }

    public BatteryLevel computeLevel(BatteryLevel batteryLevel, LocalDateTime lastBatteryUpdate,
            LocalDateTime currentTime) {
        return batteryStrategy.computeLevel(lastBatteryUpdate, currentTime, batteryLevel);
    }
}
