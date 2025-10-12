package ca.ulaval.glo4003.trotti.domain.scooter.values;

import java.time.LocalDateTime;

public interface BatteryStrategy {
    Battery calculateBatteryValue(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            Battery battery);
}
