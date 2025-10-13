package ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryLevel;
import java.time.LocalDateTime;

public interface BatteryStrategy {
    BatteryLevel computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            BatteryLevel batteryLevel);
}
