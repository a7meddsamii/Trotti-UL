package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StationTest {
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private DockingArea A_DOCKING_AREA;
    private Location A_LOCATION;
    private Station station;

    @BeforeEach
    void setup() {
        A_DOCKING_AREA = Mockito.mock(DockingArea.class);
        A_LOCATION = Mockito.mock(Location.class);
        station = new Station(A_LOCATION, A_DOCKING_AREA);
    }

    @Test
    void givenSlotNumber_whenGetScooter_thenReturnsScooterIdFromDockingAreaAndCallsUndockOnDockingArea() {
        Mockito.when(A_DOCKING_AREA.undock(SLOT_NUMBER)).thenReturn(A_SCOOTER_ID);

        ScooterId result = station.getScooter(SLOT_NUMBER);

        Assertions.assertEquals(A_SCOOTER_ID, result);
        Mockito.verify(A_DOCKING_AREA).undock(SLOT_NUMBER);
    }

    @Test
    void givenSlotNumberAndScooterId_whenReturnScooter_thenCallsDockOnDockingArea() {
        station.returnScooter(SLOT_NUMBER, A_SCOOTER_ID);

        Mockito.verify(A_DOCKING_AREA).dock(SLOT_NUMBER, A_SCOOTER_ID);
    }

    @Test
    void givenStationWithCapacityOf10_whenCalculateInitialScooterCount_thenReturns80PercentOfCapacity() {
        Mockito.when(A_DOCKING_AREA.getCapacity()).thenReturn(10);
        Station stationWithCapacity = new Station(A_LOCATION, A_DOCKING_AREA);

        int result = stationWithCapacity.calculateInitialScooterCount();

        Assertions.assertEquals(8, result);
    }
}
