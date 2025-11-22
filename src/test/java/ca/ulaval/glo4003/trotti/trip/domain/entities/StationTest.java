package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class StationTest {
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final SlotNumber SLOT_NUMBER_2 = new SlotNumber(2);
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private static final ScooterId ANOTHER_SCOOTER_ID = ScooterId.randomId();
    private static final Idul TECHNICIAN_ID = Idul.from("anIdul");
    private static final Idul OTHER_TECHNICIAN_ID = Idul.from("otherTech");

    private DockingArea dockingArea;
    private Location A_LOCATION;
    private Station station;
    private Scooter scooter;

    @BeforeEach
    void setup() {
        dockingArea = Mockito.mock(DockingArea.class);
        A_LOCATION = Mockito.mock(Location.class);
        station = new Station(A_LOCATION, dockingArea);
        scooter = Mockito.mock(Scooter.class);
    }

    @Test
    void givenSlotNumber_whenGetScooter_thenReturnsScooterIdFromDockingAreaAndCallsUndockOnDockingArea() {
        Mockito.when(dockingArea.undock(SLOT_NUMBER)).thenReturn(A_SCOOTER_ID);

        ScooterId result = station.getScooter(SLOT_NUMBER);

        Assertions.assertEquals(A_SCOOTER_ID, result);
        Mockito.verify(dockingArea).undock(SLOT_NUMBER);
    }

    @Test
    void givenSlotNumberAndScooter_whenReturnScooter_thenScooterIsDockedAndCallsDockingArea() {
        Mockito.when(scooter.getScooterId()).thenReturn(A_SCOOTER_ID);
        station.returnScooter(SLOT_NUMBER, scooter.getScooterId());

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, A_SCOOTER_ID);
    }

    @Test
    void givenStationWithCapacityOf10_whenCalculateInitialScooterCount_thenReturns80PercentOfCapacity() {
        Mockito.when(dockingArea.getCapacity()).thenReturn(10);
        Station stationWithCapacity = new Station(A_LOCATION, dockingArea);

        int result = stationWithCapacity.calculateInitialScooterCount();

        Assertions.assertEquals(8, result);
    }

    @Test
    void givenStationAlreadyUnderMaintenance_whenStartMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID);

        Executable action = () -> station.startMaintenance(TECHNICIAN_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationNotUnderMaintenance_whenEndMaintenance_thenThrowsException() {
        Executable action = () -> station.endMaintenance(TECHNICIAN_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenDifferentTechnician_whenEndMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID);

        Executable action = () -> station.endMaintenance(OTHER_TECHNICIAN_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationUnderMaintenance_whenGetScooter_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID);

        Executable action = () -> station.getScooter(SLOT_NUMBER);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationUnderMaintenance_whenReturnScooter_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID);

        Executable action = () -> station.returnScooter(SLOT_NUMBER, A_SCOOTER_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenSlotNumbers_whenRetrieveScootersForTransfer_thenReturnsScooterIds() {
        List<SlotNumber> slots = List.of(SLOT_NUMBER, SLOT_NUMBER_2);
        Mockito.when(dockingArea.undock(SLOT_NUMBER)).thenReturn(A_SCOOTER_ID);
        Mockito.when(dockingArea.undock(SLOT_NUMBER_2)).thenReturn(ANOTHER_SCOOTER_ID);

        Set<ScooterId> result = station.retrieveScootersForTransfer(slots);

        Assertions.assertEquals(Set.of(A_SCOOTER_ID, ANOTHER_SCOOTER_ID), result);
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
    void givenStationUnderMaintenanceWithCorrectTechnician_whenValidateTechnicianInCharge_thenDoesNotThrow() {
        station.startMaintenance(TECHNICIAN_ID);

        Assertions.assertDoesNotThrow(() -> station.validateTechnicianInCharge(TECHNICIAN_ID));
    }

    @Test
    void givenStationNotUnderMaintenance_whenValidateTechnicianInCharge_thenThrowsException() {
        Executable action = () -> station.validateTechnicianInCharge(TECHNICIAN_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationUnderMaintenanceWithDifferentTechnician_whenValidateTechnicianInCharge_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID);

        Executable action = () -> station.validateTechnicianInCharge(OTHER_TECHNICIAN_ID);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenSlotsAndScooterIds_whenReturnScooters_thenCallsReturnScooterForEach() {
        List<SlotNumber> slots = List.of(SLOT_NUMBER, SLOT_NUMBER_2);
        List<ScooterId> scooterIds = List.of(A_SCOOTER_ID, ANOTHER_SCOOTER_ID);

        station.returnScooters(slots, scooterIds);

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, A_SCOOTER_ID);
        Mockito.verify(dockingArea).dock(SLOT_NUMBER_2, ANOTHER_SCOOTER_ID);
    }
}
