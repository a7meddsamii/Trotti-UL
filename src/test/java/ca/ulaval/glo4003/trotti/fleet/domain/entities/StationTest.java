package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class StationTest {
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final SlotNumber SLOT_NUMBER_2 = new SlotNumber(2);
    private static final Idul TECHNICIAN_ID = Idul.from("anIdul");
    private static final Idul OTHER_TECHNICIAN_ID = Idul.from("otherTech");
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.of(2024, 1, 1, 12, 30);
	private static final int EXPECTED_INITIAL_CAPACITY = 8;
	private static final int CAPACITY = 10;

    private DockingArea dockingArea;
    private Location location;
    private Station station;
    private Scooter scooter;

    @BeforeEach
    void setup() {
        dockingArea = Mockito.mock(DockingArea.class);
        location = Mockito.mock(Location.class);
        station = new Station(location, dockingArea);
        scooter = Mockito.mock(Scooter.class);
    }

    @Test
    void givenStation_whenCalculateInitialScooterCount_thenReturns80PercentOfCapacity() {
        Mockito.when(dockingArea.getCapacity()).thenReturn(CAPACITY);

        int initialScooterCount = station.calculateInitialScooterCount();

        Assertions.assertEquals(EXPECTED_INITIAL_CAPACITY, initialScooterCount);
    }

    @Test
    void givenAvailableScooter_whenTakeScooter_thenCompletesUndockingProcess() {
        Mockito.when(dockingArea.undock(SLOT_NUMBER)).thenReturn(scooter);

        Scooter result = station.takeScooter(SLOT_NUMBER, CURRENT_TIME);

        Assertions.assertEquals(scooter, result);
        Mockito.verify(dockingArea).undock(SLOT_NUMBER);
        Mockito.verify(scooter).beginUsage(CURRENT_TIME);
    }

    @Test
    void givenStationUnderMaintenance_whenTakeScooter_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Executable action = () -> station.takeScooter(SLOT_NUMBER, CURRENT_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenScooterAndSlot_whenParkScooter_thenCompletesDockingProcess() {
        station.parkScooter(SLOT_NUMBER, scooter, CURRENT_TIME);

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, scooter);
        Mockito.verify(scooter).endUsage(location, CURRENT_TIME);
    }

    @Test
    void givenStationUnderMaintenance_whenParkScooter_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Executable action = () -> station.parkScooter(SLOT_NUMBER, scooter, CURRENT_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationNotUnderMaintenance_whenStartMaintenance_thenSwitchToUnderMaintenanceStatus() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Mockito.verify(dockingArea).turnOffElectricity(CURRENT_TIME);
        Assertions.assertTrue(station.getMaintenanceStatus().isActive());
    }

    @Test
    void givenStationAlreadyUnderMaintenance_whenStartMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Executable action = () -> station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationNotUnderMaintenance_whenEndMaintenance_thenThrowsException() {
        Executable action = () -> station.endMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenDifferentTechnician_whenEndMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Executable action = () -> station.endMaintenance(OTHER_TECHNICIAN_ID, CURRENT_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationUnderMaintenance_whenEndMaintenance_thenSwitchMaintenanceStatusToCompleted() {
        station.startMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        station.endMaintenance(TECHNICIAN_ID, CURRENT_TIME);

        Mockito.verify(dockingArea).turnOnElectricity(CURRENT_TIME);
        Assertions.assertFalse(station.getMaintenanceStatus().isActive());
    }

    @Test
    void givenSlotNumbers_whenRetrieveScootersForTransfer_thenUndocksCorrespondingAmountOfScooters() {
        List<SlotNumber> slots = List.of(SLOT_NUMBER, SLOT_NUMBER_2);
        Scooter scooter2 = Mockito.mock(Scooter.class);
        Mockito.when(dockingArea.undock(SLOT_NUMBER)).thenReturn(scooter);
        Mockito.when(dockingArea.undock(SLOT_NUMBER_2)).thenReturn(scooter2);

        List<Scooter> result = station.retrieveScootersForTransfer(slots);

        Assertions.assertEquals(List.of(scooter, scooter2), result);
    }

    @Test
    void givenStation_whenGetAvailableSlots_thenReturnsValuesFromDockingArea() {
        List<SlotNumber> expected = List.of(SLOT_NUMBER, SLOT_NUMBER_2);
        Mockito.when(dockingArea.findAvailableSlots()).thenReturn(expected);

        List<SlotNumber> result = station.getAvailableSlots();

        Assertions.assertEquals(expected, result);
        Mockito.verify(dockingArea).findAvailableSlots();
    }

    @Test
    void givenStation_whenGetOccupiedSlots_thenReturnsValuesFromDockingArea() {
        List<SlotNumber> expected = List.of(SLOT_NUMBER);
        Mockito.when(dockingArea.findOccupiedSlots()).thenReturn(expected);

        List<SlotNumber> result = station.getOccupiedSlots();

        Assertions.assertEquals(expected, result);
        Mockito.verify(dockingArea).findOccupiedSlots();
    }

    @Test
    void givenMultipleSlotsAndScooters_whenParkScooters_thenParksEachScooter() {
        Scooter scooter2 = Mockito.mock(Scooter.class);
        List<SlotNumber> slots = List.of(SLOT_NUMBER, SLOT_NUMBER_2);
        List<Scooter> scooters = List.of(scooter, scooter2);

        station.parkScooters(slots, scooters, CURRENT_TIME);

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, scooter);
        Mockito.verify(dockingArea).dock(SLOT_NUMBER_2, scooter2);
    }
}
