package ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;

import java.time.LocalDateTime;

public interface BatteryStrategy {
    Battery computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
						 Battery battery);
}
