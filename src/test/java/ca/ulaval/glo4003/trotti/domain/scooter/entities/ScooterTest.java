package ca.ulaval.glo4003.trotti.domain.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.BatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

public class ScooterTest {
    private static final Id AN_ID = Id.randomId();
    private static final Battery A_BATTERY = Battery.from(BigDecimal.valueOf(50));
    private BatteryStrategy chargeStrategy;
    private BatteryStrategy dischargeStrategy;
    private static final LocalDateTime A_LAST_BATTERY_UPDATE = LocalDateTime.now().minusMinutes(5);
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
    private Scooter A_SCOOTER;

    @BeforeEach
    void setup() {
        chargeStrategy = Mockito.mock(BatteryStrategy.class);
        dischargeStrategy = Mockito.mock(BatteryStrategy.class);
        EnumMap<BatteryState, BatteryStrategy> batteryStrategyMap = new EnumMap<>(Map.of(
				BatteryState.CHARGING, chargeStrategy,
				BatteryState.DISCHARGING, dischargeStrategy
		));
        A_SCOOTER = new Scooter(AN_ID, A_BATTERY, batteryStrategyMap, A_LAST_BATTERY_UPDATE,BatteryState.CHARGING);
    }

    @Test
    public void givenChargeBatteryState_whenUpdateBattery_thenUsesChargeStrategy() {
        BatteryState CHARGE_BATTERY_STATE = BatteryState.CHARGING;

        A_SCOOTER.updateBatteryState(CHARGE_BATTERY_STATE, CURRENT_TIME);

        Mockito.verify(chargeStrategy).computeLevel(A_LAST_BATTERY_UPDATE, CURRENT_TIME,
													A_BATTERY);
    }

    @Test
    public void givenDischargeBatteryState_whenUpdateBattery_thenUsesDischargeStrategy() {
        BatteryState DISCHARGE_BATTERY_STATE = BatteryState.DISCHARGING;

        A_SCOOTER.updateBatteryState(DISCHARGE_BATTERY_STATE, CURRENT_TIME);

        Mockito.verify(dischargeStrategy).computeLevel(A_LAST_BATTERY_UPDATE, CURRENT_TIME,
													   A_BATTERY);
    }
}
