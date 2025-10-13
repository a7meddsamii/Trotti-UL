package ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class DischargeBatteryStrategy implements BatteryStrategy {
    private static final BigDecimal RATE_OF_CHANGE_PER_MINUTE = BigDecimal.valueOf(-0.5);

    @Override
    public Battery computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
								Battery battery) {
        long minutesElapsed = Duration.between(lastBatteryUpdate, currentTime).toMinutes();
        BigDecimal batteryDelta =
                RATE_OF_CHANGE_PER_MINUTE.multiply(BigDecimal.valueOf(minutesElapsed));
		
        return battery.applyDelta(batteryDelta);
    }
}
