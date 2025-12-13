package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryState;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BatteryTest {
    private static final BatteryLevel A_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.valueOf(50));
    private static final LocalDateTime LAST_BATTERY_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 30);
    private static final BatteryLevel BELOW_MINIMUM_OPERATIONAL_LEVEL =
            BatteryLevel.from(BigDecimal.valueOf(10));
    private static final BatteryLevel ABOVE_MINIMUM_OPERATIONAL_LEVEL =
            BatteryLevel.from(BigDecimal.valueOf(20));
    private Battery battery;

    @BeforeEach
    void setup() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING);
    }

    @Test
    void givenUpdateTimeBeforeLastUpdate_whenChangeBatteryState_thenThrowsException() {
        LocalDateTime updateTime = LAST_BATTERY_UPDATE.minusMinutes(1);

        Executable changeState =
                () -> battery.changeBatteryState(BatteryState.DISCHARGING, updateTime);

        Assertions.assertThrows(InvalidBatteryUpdate.class, changeState);
    }

    @Test
    void givenDischargingBattery_whenChangeBatteryState_thenComputesLostCharge() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.DISCHARGING);
        BatteryLevel oldBatteryLevel = battery.getBatteryLevel();

        battery.changeBatteryState(BatteryState.CHARGING, CURRENT_TIME);

        Assertions.assertFalse(battery.getBatteryLevel().isGreaterThan(oldBatteryLevel));
        Assertions.assertNotEquals(oldBatteryLevel, battery.getBatteryLevel());
    }

    @Test
    void givenChargingBattery_whenChangeBatteryState_thenComputesGainedCharge() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING);
        BatteryLevel oldBatteryLevel = battery.getBatteryLevel();

        battery.changeBatteryState(BatteryState.DISCHARGING, CURRENT_TIME);

        Assertions.assertFalse(oldBatteryLevel.isGreaterThan(battery.getBatteryLevel()));
        Assertions.assertNotEquals(oldBatteryLevel, battery.getBatteryLevel());
    }

    @Test
    void givenSameState_whenChangeBatteryState_thenDoesNotComputeCharge() {
        BatteryLevel oldBatteryLevel = battery.getBatteryLevel();

        battery.changeBatteryState(BatteryState.CHARGING, CURRENT_TIME);

        Assertions.assertEquals(oldBatteryLevel, battery.getBatteryLevel());
    }

    @Test
    void whenHasEnoughCharge_thenReturnsTrue() {
        battery = new Battery(ABOVE_MINIMUM_OPERATIONAL_LEVEL, LAST_BATTERY_UPDATE,
                BatteryState.CHARGING);

        Assertions.assertTrue(battery.hasEnoughCharge());
    }

    @Test
    void whenDoesNotHaveEnoughCharge_thenReturnsFalse() {
        battery = new Battery(BELOW_MINIMUM_OPERATIONAL_LEVEL, LAST_BATTERY_UPDATE,
                BatteryState.CHARGING);

        Assertions.assertFalse(battery.hasEnoughCharge());
    }

    @Test
    void givenChargingBattery_whenPauseCharging_thenChangesToIdleState() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING);

        battery.pauseCharging(CURRENT_TIME);

        Assertions.assertEquals(BatteryState.IDLE, battery.getCurrentBatteryState());
    }

    @Test
    void givenNonChargingBattery_whenPauseCharging_thenStateUnchanged() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.DISCHARGING);

        battery.pauseCharging(CURRENT_TIME);

        Assertions.assertEquals(BatteryState.DISCHARGING, battery.getCurrentBatteryState());
    }

    @Test
    void givenIdleBattery_whenResumeCharging_thenChangesToChargingState() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.IDLE);

        battery.resumeCharging(CURRENT_TIME);

        Assertions.assertEquals(BatteryState.CHARGING, battery.getCurrentBatteryState());
    }

    @Test
    void givenNonIdleBattery_whenResumeCharging_thenStateUnchanged() {
        battery = new Battery(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.DISCHARGING);

        battery.resumeCharging(CURRENT_TIME);

        Assertions.assertEquals(BatteryState.DISCHARGING, battery.getCurrentBatteryState());
    }
}
