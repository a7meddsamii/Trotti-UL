package ca.ulaval.glo4003.trotti.domain.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.scooter.values.BatteryState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ScooterTest {
	private static final Id AN_ID = Id.randomId();
	private static final Battery A_BATTERY = Battery.from(BigDecimal.valueOf(50));
	private static final LocalDateTime LAST_BATTERY_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
	private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
	private Scooter A_SCOOTER;
	
	@BeforeEach
	void setup() {
		A_SCOOTER = new Scooter(AN_ID, A_BATTERY, LAST_BATTERY_UPDATE, BatteryState.CHARGING);
	}
	
	@Test
	void givenUpdateTimeBeforeLastUpdate_whenUpdateState_thenThrowsException(){
		LocalDateTime newUpdateTime = LAST_BATTERY_UPDATE.minusMinutes(1);
		
		Executable updateState = () -> A_SCOOTER.updateBatteryState(BatteryState.CHARGING, newUpdateTime);
		
		Assertions.assertThrows(InvalidBatteryUpdate.class, updateState);
	}
	
	@Test
	void givenSameState_whenUpdateState_thenDoesNotUpdateScooterInfo() {
		A_SCOOTER = new Scooter(AN_ID, A_BATTERY, LAST_BATTERY_UPDATE, BatteryState.CHARGING);
		
		A_SCOOTER.updateBatteryState(BatteryState.CHARGING, CURRENT_TIME);
		
		Assertions.assertSame(A_BATTERY, A_SCOOTER.getBattery());
		Assertions.assertSame(LAST_BATTERY_UPDATE, A_SCOOTER.getLastBatteryUpdate());
	}
	
	@Test
	void givenChargingState_whenUpdateState_thenComputeNewBatteryLevel() {
		BatteryState newState = Mockito.mock(BatteryState.class);
		
		A_SCOOTER.updateBatteryState(newState, CURRENT_TIME);
		
		Mockito.verify(newState).computeLevel(A_BATTERY, LAST_BATTERY_UPDATE, CURRENT_TIME);
	}
}
