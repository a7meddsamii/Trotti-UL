package ca.ulaval.glo4003.trotti.domain.trip.scooter.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.StationLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class ScooterTest {
    private static final Id AN_ID = Id.randomId();
    private static final BatteryLevel A_BATTERY_LEVEL = BatteryLevel.from(BigDecimal.valueOf(50));
    private static final LocalDateTime LAST_BATTERY_UPDATE = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 30);
	private static final LocalDateTime FUTURE_TIME = LocalDateTime.of(2024, 1, 1, 13, 0);
	private static final Location A_LOCATION = new StationLocation("building A", "spot name");
    private Scooter scooter;
	
	@BeforeEach
	void setup() {
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING, A_LOCATION);
	}

	@Test
	void givenScooterAlreadyDocked_whenDockAt_thenThrowsException() {
		Executable dockAt = () -> scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Assertions.assertThrows(InvalidLocation.class, dockAt);
	}
	
	@Test
	void givenDockingTimeBeforeLastUpdate_whenDockAt_thenThrowsException(){
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING, Location.empty());
		LocalDateTime dockingTime = LAST_BATTERY_UPDATE.minusMinutes(1);
		
		Executable dockAt = () -> scooter.dockAt(A_LOCATION, dockingTime);
		
		Assertions.assertThrows(InvalidBatteryUpdate.class, dockAt);
	}
	
    @Test
    void givenStationLocationAndDockingTime_whenDockAt_thenComputeNewBatteryLevel() {
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.DISCHARGING, Location.empty());
		BatteryLevel expectedBatteryLevel = BatteryState.DISCHARGING.computeLevel(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, CURRENT_TIME);
		
		scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Assertions.assertEquals(expectedBatteryLevel, scooter.getBatteryLevel());
	}
		
	
	@Test
	void givenStationLocation_whenDockAt_thenStationLocationIsSetToGivenLocation(){
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING, Location.empty());
		
		scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Assertions.assertEquals(A_LOCATION, scooter.getStationLocation());
	}
	
	@Test
	void givenScooterAlreadyUndocked_whenUndock_thenThrowsException() {
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING, Location.empty());
		
		Executable undock = () -> scooter.undock(CURRENT_TIME);
		
		Assertions.assertThrows(InvalidLocation.class, undock);
	}
	
	@Test
	void givenUndockingTimeBeforeLastUpdate_whenUndock_thenThrowsException() {
		LocalDateTime undockingTime = LAST_BATTERY_UPDATE.minusMinutes(1);
		
		Executable undock = () -> scooter.undock(undockingTime);
		
		Assertions.assertThrows(InvalidBatteryUpdate.class, undock);
	}
	
	@Test
	void givenStationLocationAndUndockingTime_whenUndock_thenComputeNewBatteryLevel() {
		scooter = new Scooter(AN_ID, A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, BatteryState.CHARGING, A_LOCATION);
		BatteryLevel expectedBatteryLevel = BatteryState.CHARGING.computeLevel(A_BATTERY_LEVEL, LAST_BATTERY_UPDATE, FUTURE_TIME);
	
		scooter.undock(FUTURE_TIME);
		
		Assertions.assertEquals(expectedBatteryLevel, scooter.getBatteryLevel());
	}
}
