package ca.ulaval.glo4003.trotti.domain.scooter.values;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.DischargeBatteryStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DischargeBatteryStrategyTest {
    private final DischargeBatteryStrategy strategy = new DischargeBatteryStrategy();
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();

    @Test
    void givenNoTimeElapsed_whenCalculateBatteryValue_thenBatteryUnchanged() {
        Battery initial = Battery.from(BigDecimal.valueOf(50));

        Battery result = strategy.calculateBatteryValue(CURRENT_TIME, CURRENT_TIME, initial);

        Assertions.assertEquals(initial, result);
    }

    @Test
    void givenMinutesElapsed_whenCalculateBatteryValue_thenReturnsCorrectResult() {
        Battery initial = Battery.from(BigDecimal.valueOf(50));
        LocalDateTime later = CURRENT_TIME.plusMinutes(10);

        Battery result = strategy.calculateBatteryValue(CURRENT_TIME, later, initial);

        Battery expected = Battery.from(BigDecimal.valueOf(45));
        Assertions.assertEquals(expected, result);
    }
}
