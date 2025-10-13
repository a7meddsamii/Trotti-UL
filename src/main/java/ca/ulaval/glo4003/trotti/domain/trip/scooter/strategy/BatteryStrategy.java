package ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;

import java.time.LocalDateTime;

public interface BatteryStrategy {
    Battery calculateBatteryValue(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
								  Battery battery);
}
