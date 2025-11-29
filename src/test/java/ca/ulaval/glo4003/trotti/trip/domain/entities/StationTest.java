package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
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
    private static final LocalDateTime A_TIME = LocalDateTime.now();

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
    void givenAvailableScooter_whenTakeScooter_thenUndocksFromDockingAreaAndBeginsUsage() {
        Mockito.when(dockingArea.undock(SLOT_NUMBER)).thenReturn(scooter);

        Scooter result = station.takeScooter(SLOT_NUMBER, A_TIME);

        Assertions.assertEquals(scooter, result);
        Mockito.verify(dockingArea).undock(SLOT_NUMBER);
        Mockito.verify(scooter).beginUsage(A_TIME);
    }

    @Test
    void givenScooterAndSlot_whenParkScooter_thenDocksAndEndsUsage() {
        station.parkScooter(SLOT_NUMBER, scooter, A_TIME);

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, scooter);
        Mockito.verify(scooter).endUsage(A_LOCATION, A_TIME);
    }

    @Test
    void givenStationUnderMaintenance_whenParkScooter_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, A_TIME);

        Executable action = () -> station.parkScooter(SLOT_NUMBER, scooter, A_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenStationNotUnderMaintenance_whenStartMaintenance_thenTurnsOffElectricityAndSetsTechnician() {
        station.startMaintenance(TECHNICIAN_ID, A_TIME);

        Mockito.verify(dockingArea).turnOffElectricity(A_TIME);
    }

    @Test
    void givenStationAlreadyUnderMaintenance_whenStartMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, A_TIME);

        Executable action = () -> station.startMaintenance(TECHNICIAN_ID, A_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenDifferentTechnician_whenEndMaintenance_thenThrowsException() {
        station.startMaintenance(TECHNICIAN_ID, A_TIME);

        Executable action = () -> station.endMaintenance(OTHER_TECHNICIAN_ID, A_TIME);

        Assertions.assertThrows(StationMaintenanceException.class, action);
    }

    @Test
    void givenSlotList_whenRetrieveScootersForTransfer_thenUndocksAllScooters() {
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

        station.parkScooters(slots, scooters, A_TIME);

        Mockito.verify(dockingArea).dock(SLOT_NUMBER, scooter);
        Mockito.verify(dockingArea).dock(SLOT_NUMBER_2, scooter2);
    }
}
