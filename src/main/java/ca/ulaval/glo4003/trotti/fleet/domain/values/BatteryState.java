package ca.ulaval.glo4003.trotti.fleet.domain.values;

import ca.ulaval.glo4003.trotti.fleet.domain.strategy.BatteryStrategy;
import ca.ulaval.glo4003.trotti.fleet.domain.strategy.ChargingStrategy;
import ca.ulaval.glo4003.trotti.fleet.domain.strategy.DischargingStrategy;
import ca.ulaval.glo4003.trotti.fleet.domain.strategy.IdleStrategy;
import java.time.LocalDateTime;

public enum BatteryState {
    DISCHARGING(DischargingStrategy.INSTANCE), CHARGING(ChargingStrategy.INSTANCE), IDLE(
            IdleStrategy.INSTANCE);

    private final BatteryStrategy batteryStrategy;

    BatteryState(BatteryStrategy batteryStrategy) {
        this.batteryStrategy = batteryStrategy;
    }

    public BatteryLevel computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            BatteryLevel batteryLevel) {
        return batteryStrategy.computeLevel(lastBatteryUpdate, currentTime, batteryLevel);
    }
}
