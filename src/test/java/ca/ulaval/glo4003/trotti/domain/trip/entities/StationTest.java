package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationTest {

    private static final Location A_STATION_LOCATION = Location.of("PEPS", "Station A");
    private static final int STATION_CAPACITY = 10;

    private Station station;

    @BeforeEach
    void setup() {
        station = new Station(A_STATION_LOCATION, STATION_CAPACITY);
    }

    @Test
    void givenStation_whenGetInitialScooterCount_thenReturns80PercentOfCapacity() {
        int initialCount = station.getInitialScooterCount();
        int expectedInitialCount = 8;

        Assertions.assertEquals(expectedInitialCount, initialCount);
    }

    @Test
    void givenScooterId_whenAddScooter_thenScooterIsAddedToStation() {
        Id scooterId = Id.randomId();

        station.addScooter(scooterId);

        Assertions.assertTrue(station.getScooterIds().contains(scooterId));
    }

    @Test
    void givenMultipleScooterIds_whenAddScooter_thenAllScootersAreAdded() {
        Id scooterId1 = Id.randomId();
        Id scooterId2 = Id.randomId();

        station.addScooter(scooterId1);
        station.addScooter(scooterId2);

        Assertions.assertEquals(2, station.getScooterIds().size());
        Assertions.assertTrue(station.getScooterIds().contains(scooterId1));
        Assertions.assertTrue(station.getScooterIds().contains(scooterId2));
    }

    @Test
    void whenCreateStation_thenScooterListIsEmpty() {
        Assertions.assertTrue(station.getScooterIds().isEmpty());
    }
}