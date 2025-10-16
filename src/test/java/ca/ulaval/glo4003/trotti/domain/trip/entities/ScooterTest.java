package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryUpdate;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidBatteryValue;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryState;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class ScooterTest {
	private static final Id AN_ID = Id.randomId();
	private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 30);
	private static final LocalDateTime FUTURE_TIME = LocalDateTime.of(2024, 1, 1, 13, 0);
	private static final Location A_LOCATION = Location.of("building A", "spot name");
	private Scooter scooter;
	private Battery battery;
	
	@BeforeEach
	void setup() {
		battery = Mockito.mock(Battery.class);
		scooter = new Scooter(AN_ID, battery, A_LOCATION);
		Mockito.when(battery.hasEnoughCharge()).thenReturn(true);
	}
	
	@Test
	void givenScooterAlreadyDocked_whenDockAt_thenThrowsException() {
		Executable dockAt = () -> scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Assertions.assertThrows(InvalidLocation.class, dockAt);
	}
	
	@Test
	void givenStationLocationAndDockingTime_whenDockAt_thenBatteryStartsCharging() {
		scooter = new Scooter(AN_ID, battery, Location.empty());
		
		scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Mockito.verify(battery).changeBatteryState(BatteryState.CHARGING, CURRENT_TIME);
	}
	
	@Test
	void givenStationLocation_whenDockAt_thenStationLocationIsSetToGivenLocation() {
		scooter = new Scooter(AN_ID, battery, Location.empty());
		
		scooter.dockAt(A_LOCATION, CURRENT_TIME);
		
		Assertions.assertEquals(A_LOCATION, scooter.getLocation());
	}
	
	@Test
	void givenScooterAlreadyUndocked_whenUndock_thenThrowsException() {
		scooter = new Scooter(AN_ID, battery, Location.empty());
		
		Executable undock = () -> scooter.undock(CURRENT_TIME);
		
		Assertions.assertThrows(InvalidLocation.class, undock);
	}
	
	@Test
	void givenStationLocationAndUndockingTime_whenUndock_thenBatteryStartsDischarging() {		
		scooter.undock(FUTURE_TIME);
		
		Mockito.verify(battery).changeBatteryState(BatteryState.DISCHARGING, FUTURE_TIME);
	}
	
	@Test
	void givenNotEnoughBatteryCharge_whenUndock_thenThrowsException() {
		Mockito.when(battery.hasEnoughCharge()).thenReturn(false);
		
		Executable undock = () -> scooter.undock(CURRENT_TIME);
		
		Assertions.assertThrows(InvalidBatteryValue.class, undock);
	}
}
