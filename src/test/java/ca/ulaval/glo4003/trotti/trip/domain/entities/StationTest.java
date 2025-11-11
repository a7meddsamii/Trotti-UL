package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StationTest {
    private static final SlotNumber SLOT_NUMBER_1 = new SlotNumber(1);
    private static final SlotNumber SLOT_NUMBER_2 = new SlotNumber(2);
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private static final ScooterId ANOTHER_SCOOTER_ID = ScooterId.randomId();
    private DockingArea A_DOCKING_AREA;
    private Location A_LOCATION;
    private Station station;
    private Scooter scooter;

    @BeforeEach
    void setup() {
        A_DOCKING_AREA = Mockito.mock(DockingArea.class);
        A_LOCATION = Mockito.mock(Location.class);
        station = new Station(A_LOCATION, A_DOCKING_AREA);
        scooter = Mockito.mock(Scooter.class);
    }

    @Test
    void givenSlotNumber_whenGetScooter_thenReturnsScooterIdFromDockingAreaAndCallsUndockOnDockingArea() {
        Mockito.when(A_DOCKING_AREA.undock(SLOT_NUMBER_1)).thenReturn(A_SCOOTER_ID);

        ScooterId result = station.getScooter(SLOT_NUMBER_1);

        Assertions.assertEquals(A_SCOOTER_ID, result);
        Mockito.verify(A_DOCKING_AREA).undock(SLOT_NUMBER_1);
    }

    @Test
    void givenSlotNumberAndScooter_whenReturnScooter_thenScooterIsDockedAndCallsDockingArea() {
        LocalDateTime now = LocalDateTime.now();
        Mockito.when(scooter.getScooterId()).thenReturn(A_SCOOTER_ID);
        station.returnScooter(SLOT_NUMBER_1, scooter, now);

        Mockito.verify(scooter).dockAt(A_LOCATION, now);
        Mockito.verify(A_DOCKING_AREA).dock(SLOT_NUMBER_1, A_SCOOTER_ID);
    }

    @Test
    void givenStationWithCapacityOf10_whenCalculateInitialScooterCount_thenReturns80PercentOfCapacity() {
        Mockito.when(A_DOCKING_AREA.getCapacity()).thenReturn(10);
        Station stationWithCapacity = new Station(A_LOCATION, A_DOCKING_AREA);

        int result = stationWithCapacity.calculateInitialScooterCount();

        Assertions.assertEquals(8, result);
    }

    @Test
    void givenNewStation_whenCheckingMaintenanceStatus_thenIsNotUnderMaintenance() {
        Assertions.assertFalse(station.isUnderMaintenance());
    }

    @Test
    void givenStation_whenStartMaintenance_thenIsUnderMaintenance() {
        station.startMaintenance();

        Assertions.assertTrue(station.isUnderMaintenance());
    }

    @Test
    void givenStationUnderMaintenance_whenEndMaintenance_thenIsNotUnderMaintenance() {
        station.startMaintenance();
        station.endMaintenance();

        Assertions.assertFalse(station.isUnderMaintenance());
    }

    @Test
    void givenStationUnderMaintenance_whenGetScooter_thenThrowsStationMaintenanceException() {
        station.startMaintenance();

        Assertions.assertThrows(StationMaintenanceException.class,
                () -> station.getScooter(SLOT_NUMBER_1));
    }

    @Test
    void givenStationUnderMaintenance_whenReturnScooter_thenThrowsStationMaintenanceException() {
        station.startMaintenance();
        LocalDateTime now = LocalDateTime.now();

        Assertions.assertThrows(StationMaintenanceException.class,
                () -> station.returnScooter(SLOT_NUMBER_1, scooter, now));
    }

    @Test
    void givenSlotNumbers_whenRetrieveScootersForTransfer_thenReturnsScooterIds() {
        List<SlotNumber> slotNumbers = List.of(SLOT_NUMBER_1, SLOT_NUMBER_2);
        Mockito.when(A_DOCKING_AREA.undock(SLOT_NUMBER_1)).thenReturn(A_SCOOTER_ID);
        Mockito.when(A_DOCKING_AREA.undock(SLOT_NUMBER_2)).thenReturn(ANOTHER_SCOOTER_ID);

        List<ScooterId> result = station.retrieveScootersForTransfer(slotNumbers);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(A_SCOOTER_ID, result.get(0));
        Assertions.assertEquals(ANOTHER_SCOOTER_ID, result.get(1));
    }

    @Test
    void givenDockingAreaWithEmptySlots_whenGetAvailableSlots_thenReturnsEmptySlotNumbers() {
        ScooterSlot emptySlot = Mockito.mock(ScooterSlot.class);
        ScooterSlot occupiedSlot = Mockito.mock(ScooterSlot.class);
        Mockito.when(emptySlot.getDockedScooter()).thenReturn(Optional.empty());
        Mockito.when(occupiedSlot.getDockedScooter()).thenReturn(Optional.of(A_SCOOTER_ID));
        Mockito.when(A_DOCKING_AREA.getScooterSlots()).thenReturn(
                Map.of(SLOT_NUMBER_1, emptySlot, SLOT_NUMBER_2, occupiedSlot));

        List<SlotNumber> result = station.getAvailableSlots();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(SLOT_NUMBER_1, result.getFirst());
    }

    @Test
    void givenDockingAreaWithOccupiedSlots_whenGetOccupiedSlots_thenReturnsOccupiedSlotNumbers() {
        ScooterSlot emptySlot = Mockito.mock(ScooterSlot.class);
        ScooterSlot occupiedSlot = Mockito.mock(ScooterSlot.class);
        Mockito.when(emptySlot.getDockedScooter()).thenReturn(Optional.empty());
        Mockito.when(occupiedSlot.getDockedScooter()).thenReturn(Optional.of(A_SCOOTER_ID));
        Mockito.when(A_DOCKING_AREA.getScooterSlots()).thenReturn(
                Map.of(SLOT_NUMBER_1, emptySlot, SLOT_NUMBER_2, occupiedSlot));

        List<SlotNumber> result = station.getOccupiedSlots();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(SLOT_NUMBER_2, result.getFirst());
    }
}
