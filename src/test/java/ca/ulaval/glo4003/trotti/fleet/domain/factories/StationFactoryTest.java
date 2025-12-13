package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidStationOperation;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class StationFactoryTest {

    private static final Location LOCATION = Location.of("PEPS", "Station A");
    private static final int CAPACITY = 10;

    private StationFactory stationFactory;

    @BeforeEach
    void setup() {
        stationFactory = new StationFactory();
    }

    @Test
    void givenValidLocationAndCapacity_whenCreate_thenStationHasCorrectLocationAndCapacity() {
        Station station = stationFactory.create(LOCATION, CAPACITY);

        Assertions.assertEquals(LOCATION, station.getLocation());
        Assertions.assertEquals(CAPACITY, station.getDockingArea().getCapacity());
    }

    @Test
    void givenNonPositiveCapacity_whenCreate_thenThrowsInvalidStationOperation() {
        int invalidCapacity = 0;

        Executable action = () -> stationFactory.create(LOCATION, invalidCapacity);

        Assertions.assertThrows(InvalidStationOperation.class, action);
    }
}
