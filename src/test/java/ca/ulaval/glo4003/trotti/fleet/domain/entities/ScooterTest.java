package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidBatteryValue;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidLocationException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.BatteryState;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class ScooterTest {
    private static final ScooterId AN_ID = ScooterId.randomId();
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
    void givenScooterAlreadyDocked_whenEndUsage_thenThrowsException() {
        Executable dockAt = () -> scooter.endUsage(A_LOCATION, CURRENT_TIME);

        Assertions.assertThrows(InvalidLocationException.class, dockAt);
    }

    @Test
    void givenStationLocationAndDockingTime_whenEndUsage_thenBatteryStartsCharging() {
        scooter = new Scooter(AN_ID, battery, Location.empty());

        scooter.endUsage(A_LOCATION, CURRENT_TIME);

        Mockito.verify(battery).changeBatteryState(BatteryState.CHARGING, CURRENT_TIME);
    }

    @Test
    void givenStationLocation_whenEndUsage_thenStationLocationIsSetToGivenLocation() {
        scooter = new Scooter(AN_ID, battery, Location.empty());

        scooter.endUsage(A_LOCATION, CURRENT_TIME);

        Assertions.assertEquals(A_LOCATION, scooter.getLocation());
    }

    @Test
    void givenScooterAlreadyUndocked_whenBeginUsage_thenThrowsException() {
        scooter = new Scooter(AN_ID, battery, Location.empty());

        Executable undock = () -> scooter.beginUsage(CURRENT_TIME);

        Assertions.assertThrows(InvalidLocationException.class, undock);
    }

    @Test
    void givenStationLocationAndUndockingTime_whenBeginUsage_thenBatteryStartsDischarging() {
        scooter.beginUsage(FUTURE_TIME);

        Mockito.verify(battery).changeBatteryState(BatteryState.DISCHARGING, FUTURE_TIME);
    }

    @Test
    void givenStationLocation_whenBeginUsage_thenScooterLocationBecomesEmpty() {
        scooter.beginUsage(CURRENT_TIME);

        Assertions.assertTrue(scooter.getLocation().isEmpty());
    }

    @Test
    void givenNotEnoughBatteryCharge_whenBeginUsage_thenThrowsException() {
        Mockito.when(battery.hasEnoughCharge()).thenReturn(false);

        Executable undock = () -> scooter.beginUsage(CURRENT_TIME);

        Assertions.assertThrows(InvalidBatteryValue.class, undock);
    }

    @Test
    void givenTime_whenPauseCharging_thenCallsBatteryPauseCharging() {
        scooter.pauseCharging(CURRENT_TIME);

        Mockito.verify(battery).pauseCharging(CURRENT_TIME);
    }

    @Test
    void givenTime_whenResumeCharging_thenCallsBatteryResumeCharging() {
        scooter.resumeCharging(FUTURE_TIME);

        Mockito.verify(battery).resumeCharging(FUTURE_TIME);
    }
}
