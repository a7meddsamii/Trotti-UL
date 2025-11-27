package ca.ulaval.glo4003.trotti.trip.domain.factories;

import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class StationFactoryTest {

    private static final Location A_LOCATION = Location.of("PEPS", "Station A");
    private static final int A_CAPACITY = 10;
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);

    private StationFactory stationFactory;

    @BeforeEach
    void setup() {
        stationFactory = new StationFactory();
    }

    @Test
    void givenLocationAndCapacity_whenCreate_thenCreatesStationWithCorrectConfiguration() {
        Station station = stationFactory.create(A_LOCATION, A_CAPACITY);

        Assertions.assertEquals(A_LOCATION, station.getLocation());
        Assertions.assertEquals(A_CAPACITY, station.getDockingArea().getCapacity());
    }

    @Test
    void givenLocationAndCapacity_whenCreate_thenCreatesStationWithEmptyDockingArea() {
        Station station = stationFactory.create(A_LOCATION, A_CAPACITY);

        Executable undockEmptySlot = () -> station.getScooter(SLOT_NUMBER);

        Assertions.assertThrows(DockingException.class, undockEmptySlot);
    }
}
