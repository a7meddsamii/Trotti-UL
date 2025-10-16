package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidDock;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidUndock;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

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
    void givenScooterAlreadyInStation_whenDockScooter_thenThrowsInvalidDockException() {
        station.dockScooter(A_SCOOTER_ID);

        Executable dock = () -> station.dockScooter(A_SCOOTER_ID);

        Assertions.assertThrows(InvalidDock.class, dock);
    }

    @Test
    void givenStationAtMaximumCapacity_whenDockScooter_thenThrowsInvalidDockException() {
        station.dockScooter(A_SCOOTER_ID);
        station.dockScooter(ANOTHER_SCOOTER_ID);

        Executable dock = () -> station.dockScooter(THIRD_SCOOTER_ID);

        Assertions.assertThrows(InvalidDock.class, dock);
    }

    @Test
    void givenStation_whenUndockScooter_thenScooterIsRemovedFromStation() {
        station.dockScooter(A_SCOOTER_ID);

        station.undockScooter(A_SCOOTER_ID);

        Assertions.assertFalse(scootersInStation.contains(A_SCOOTER_ID));
    }

    @Test
    void givenScooterNotInStation_whenUndockScooter_thenThrowsInvalidUndockException() {
        Executable undock = () -> station.undockScooter(A_SCOOTER_ID);

        Assertions.assertThrows(InvalidUndock.class, undock);
    }
}
