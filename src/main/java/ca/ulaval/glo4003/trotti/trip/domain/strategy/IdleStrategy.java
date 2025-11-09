package ca.ulaval.glo4003.trotti.trip.domain.strategy;

import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import java.time.LocalDateTime;

public enum IdleStrategy implements BatteryStrategy {
    INSTANCE;

    @Override
    public BatteryLevel computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            BatteryLevel batteryLevel) {
        return batteryLevel;
    }
}