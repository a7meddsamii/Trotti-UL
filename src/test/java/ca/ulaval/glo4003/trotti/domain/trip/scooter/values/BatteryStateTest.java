package ca.ulaval.glo4003.trotti.domain.trip.scooter.values;

import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.ChargeBatteryStrategy;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.strategy.DischargeBatteryStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class BatteryStateTest {
	private static final LocalDateTime LAST_BATTERY_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
	private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 10);
	private static final Battery A_BATTERY = Battery.from(BigDecimal.TEN);
	
	@Test
	void givenDischargingState_whenComputeLevel_thenUsesDischargeStrategy() {
		Battery expectedResult = DischargeBatteryStrategy.INSTANCE.computeLevel(LAST_BATTERY_UPDATE, CURRENT_TIME, A_BATTERY);
		
		Battery result = BatteryState.DISCHARGING.computeLevel(A_BATTERY, LAST_BATTERY_UPDATE, CURRENT_TIME);
		
		Assertions.assertEquals(expectedResult, result);
	}
	
	@Test
	void givenChargingState_whenComputeLevel_thenUsesChargeStrategy() {
		Battery expectedResult = ChargeBatteryStrategy.INSTANCE.computeLevel(LAST_BATTERY_UPDATE, CURRENT_TIME, A_BATTERY);
		
		Battery result = BatteryState.CHARGING.computeLevel(A_BATTERY, LAST_BATTERY_UPDATE, CURRENT_TIME);
		
		Assertions.assertEquals(expectedResult, result);
	}
}