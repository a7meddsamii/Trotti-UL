package ca.ulaval.glo4003.trotti.domain.trip.strategy;

import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public enum ChargingStrategy implements BatteryStrategy {
    INSTANCE;

    private static final BigDecimal RATE_OF_CHANGE_PER_MINUTE = BigDecimal.valueOf(0.2);

    @Override
    public BatteryLevel computeLevel(LocalDateTime lastBatteryUpdate, LocalDateTime currentTime,
            BatteryLevel batteryLevel) {
        long minutesElapsed = Duration.between(lastBatteryUpdate, currentTime).toMinutes();
        BigDecimal batteryDelta =
                RATE_OF_CHANGE_PER_MINUTE.multiply(BigDecimal.valueOf(minutesElapsed));

        return batteryLevel.applyDelta(batteryDelta);
    }
}
