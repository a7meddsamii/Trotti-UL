package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidStation;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class StationTest {

    private static final Id A_SCOOTER_ID = Id.randomId();
    private static final Id ANOTHER_SCOOTER_ID = Id.randomId();
    private static final Id THIRD_SCOOTER_ID = Id.randomId();
    private static final int A_MAXIMUM_CAPACITY = 2;
    private final List<Id> scootersInStation = new ArrayList<>();
    private Station station;

    @BeforeEach
    void setup() {
        Location location = Mockito.mock(Location.class);
        station = new Station(location, scootersInStation, A_MAXIMUM_CAPACITY);
    }

    @Test
    void givenStation_whenDockScooter_thenScooterIsAddedToStation() {
        station.dockScooter(A_SCOOTER_ID);

        Assertions.assertTrue(scootersInStation.contains(A_SCOOTER_ID));
    }

    @Test
    void givenScooterAlreadyInStation_whenDockScooter_thenThrowInvalidStationException() {
        station.dockScooter(A_SCOOTER_ID);

        Executable executable = () -> station.dockScooter(A_SCOOTER_ID);

        Assertions.assertThrows(InvalidStation.class, executable);
    }

    @Test
    void givenStationAtMaximumCapacity_whenDockScooter_thenThrowInvalidStation() {
        station.dockScooter(A_SCOOTER_ID);
        station.dockScooter(ANOTHER_SCOOTER_ID);

        Executable executable = () -> station.dockScooter(THIRD_SCOOTER_ID);

        Assertions.assertThrows(InvalidStation.class, executable);
    }

    @Test
    void whenUndockScooter_thenScooterIsRemovedFromStation() {
        station.dockScooter(A_SCOOTER_ID);

        station.undockScooter(A_SCOOTER_ID);

        Assertions.assertFalse(scootersInStation.contains(A_SCOOTER_ID));
    }

    @Test
    void givenScooterNotInStation_whenUndockScooter_thenThrowInvalidStation() {
        Executable executable = () -> station.undockScooter(A_SCOOTER_ID);

        Assertions.assertThrows(InvalidStation.class, executable);
    }
}
