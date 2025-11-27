package ca.ulaval.glo4003.trotti.fleet.domain.strategy;

import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import java.time.LocalDateTime;

public interface BatteryStrategy {
    BatteryLevel computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            BatteryLevel batteryLevel);
}
