package ca.ulaval.glo4003.trotti.domain.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.BatteryStrategy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        Map<BatteryState, BatteryStrategy> batteryStrategyMap = Map.of(BatteryState.CHARGING,
																	   chargeStrategy, BatteryState.DISCHARGING, dischargeStrategy);
        A_SCOOTER = new Scooter(AN_ID, A_BATTERY, batteryStrategyMap, A_LAST_BATTERY_UPDATE);
    }

    @Test
    public void givenChargeBatteryState_whenCalculateBatteryValue_thenUsesChargeStrategy() {
        BatteryState CHARGE_BATTERY_STATE = BatteryState.CHARGING;

        A_SCOOTER.calculateBatteryValue(CHARGE_BATTERY_STATE, CURRENT_TIME);

        Mockito.verify(chargeStrategy).calculateBatteryValue(A_LAST_BATTERY_UPDATE, CURRENT_TIME,
                A_BATTERY);
    }

    @Test
    public void givenDischargeBatteryState_whenCalculateBatteryValue_thenUsesDischargeStrategy() {
        BatteryState DISCHARGE_BATTERY_STATE = BatteryState.DISCHARGING;

        A_SCOOTER.calculateBatteryValue(DISCHARGE_BATTERY_STATE, CURRENT_TIME);

        Mockito.verify(dischargeStrategy).calculateBatteryValue(A_LAST_BATTERY_UPDATE, CURRENT_TIME,
                A_BATTERY);
    }
}
