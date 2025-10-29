package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.trip.domain.strategy.ChargingStrategy;
import ca.ulaval.glo4003.trotti.trip.domain.strategy.DischargingStrategy;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BatteryLevelStateTest {
    private static final LocalDateTime LAST_BATTERY_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 10);
    private static final BatteryLevel A_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.TEN);

    @Test
    void givenDischargingState_whenComputeLevel_thenUsesDischargeStrategy() {
        BatteryLevel expectedResult = DischargingStrategy.INSTANCE.computeLevel(LAST_BATTERY_UPDATE,
                CURRENT_TIME, A_BATTERY_LEVEL);

        BatteryLevel result = BatteryState.DISCHARGING.computeLevel(LAST_BATTERY_UPDATE,
																	CURRENT_TIME, A_BATTERY_LEVEL);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenChargingState_whenComputeLevel_thenUsesChargeStrategy() {
        BatteryLevel expectedResult = ChargingStrategy.INSTANCE.computeLevel(LAST_BATTERY_UPDATE,
                CURRENT_TIME, A_BATTERY_LEVEL);

        BatteryLevel result = BatteryState.CHARGING.computeLevel(LAST_BATTERY_UPDATE, CURRENT_TIME,
                A_BATTERY_LEVEL);

        Assertions.assertEquals(expectedResult, result);
    }
}
