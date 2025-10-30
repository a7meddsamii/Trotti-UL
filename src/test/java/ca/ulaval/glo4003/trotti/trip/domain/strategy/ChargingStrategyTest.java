package ca.ulaval.glo4003.trotti.trip.domain.strategy;

import ca.ulaval.glo4003.trotti.trip.domain.strategy.ChargingStrategy;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChargingStrategyTest {
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final BatteryLevel UPDATED_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.TEN);
    private static final BatteryLevel INITIAL_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.ZERO);
    private static final long ELAPSED_MINUTES = 50;
    private final ChargingStrategy strategy = ChargingStrategy.INSTANCE;

    @Test
    void givenNoTimeElapsed_whenComputeLevel_thenBatteryUnchanged() {
        BatteryLevel result =
                strategy.computeLevel(CURRENT_TIME, CURRENT_TIME, INITIAL_BATTERY_LEVEL);

        Assertions.assertEquals(INITIAL_BATTERY_LEVEL, result);
    }

    @Test
    void givenMinutesElapsed_whenComputeLevel_thenReturnsCorrectResult() {
        LocalDateTime later = CURRENT_TIME.plusMinutes(ELAPSED_MINUTES);

        BatteryLevel result = strategy.computeLevel(CURRENT_TIME, later, INITIAL_BATTERY_LEVEL);

        Assertions.assertEquals(UPDATED_BATTERY_LEVEL, result);
    }
}
