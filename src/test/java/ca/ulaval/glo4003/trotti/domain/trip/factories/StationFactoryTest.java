package ca.ulaval.glo4003.trotti.domain.trip.factories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
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

        assertThat(A_LOCATION).isEqualTo(station.getLocation());
        assertThat(A_CAPACITY).isEqualTo(station.getDockingArea().getCapacity());
    }

    @Test
    void givenLocationAndCapacity_whenCreate_thenCreatesStationWithEmptyDockingArea() {
        Station station = stationFactory.create(A_LOCATION, A_CAPACITY);

        Executable undockEmptySlot = () -> station.getScooter(SLOT_NUMBER);

        assertThrows(DockingException.class, undockEmptySlot);
    }
}
