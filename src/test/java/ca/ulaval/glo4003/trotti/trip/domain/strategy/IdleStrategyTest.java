package ca.ulaval.glo4003.trotti.trip.domain.strategy;

import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IdleStrategyTest {

    @Test
    void givenBatteryLevel_whenComputeLevel_thenReturnsSameBatteryLevel() {
        BatteryLevel initialLevel = BatteryLevel.from(BigDecimal.valueOf(50));
        LocalDateTime lastUpdate = LocalDateTime.now().minusHours(2);
        LocalDateTime currentTime = LocalDateTime.now();

        BatteryLevel result =
                IdleStrategy.INSTANCE.computeLevel(lastUpdate, currentTime, initialLevel);

        Assertions.assertEquals(initialLevel, result);
    }
}
