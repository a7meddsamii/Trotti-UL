package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidStationOperation;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class FleetTest {

    private static final Location A_LOCATION = Location.of("Vachon", "porte1");
    private static final SlotNumber SLOT_1 = new SlotNumber(1);
    private static final SlotNumber SLOT_2 = new SlotNumber(2);
    private static final LocalDateTime A_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final Idul TECHNICIAN = Idul.from("tech123");

    private Station station;
    private Scooter scooter;
    private ScooterId scooterId;
    private Transfer transfer;
    private Fleet fleet;

    @BeforeEach
    void setup() {
        station = Mockito.mock(Station.class);
        scooter = Mockito.mock(Scooter.class);
        scooterId = ScooterId.randomId();
        transfer = Mockito.mock(Transfer.class);

        Mockito.when(scooter.getScooterId()).thenReturn(scooterId);

        fleet = new Fleet(Map.of(A_LOCATION, station), new HashMap<>(), new HashMap<>());
    }

    @Test
    void givenAvailableScooterAtStation_whenRentScooter_thenScooterIdIsReturned() {
        Mockito.when(station.takeScooter(SLOT_1, A_TIME)).thenReturn(scooter);

        ScooterId result = fleet.rentScooter(A_LOCATION, SLOT_1, A_TIME);

        Assertions.assertEquals(scooterId, result);
    }

    @Test
    void givenStationTakeFails_whenRentScooter_thenThrowsInvalidStationOperation() {
        Mockito.when(station.takeScooter(SLOT_1, A_TIME))
                .thenThrow(new InvalidStationOperation("fail"));

        Executable action = () -> fleet.rentScooter(A_LOCATION, SLOT_1, A_TIME);

        Assertions.assertThrows(InvalidStationOperation.class, action);
    }

    @Test
    void givenUnknownScooterId_whenReturnScooter_thenThrowsInvalidStationOperation() {
        Executable action = () -> fleet.returnScooter(scooterId, A_LOCATION, SLOT_2, A_TIME);

        Assertions.assertThrows(InvalidStationOperation.class, action);
    }

    @Test
    void givenRentedScooter_whenReturnScooter_thenStationParksScooter() {
        Mockito.when(station.takeScooter(SLOT_1, A_TIME)).thenReturn(scooter);
        fleet.rentScooter(A_LOCATION, SLOT_1, A_TIME);

        fleet.returnScooter(scooterId, A_LOCATION, SLOT_2, A_TIME);

        Mockito.verify(station).parkScooter(SLOT_2, scooter, A_TIME);
    }

    @Test
    void givenTechnician_whenStartMaintenance_thenStationStartsMaintenance() {
        fleet.startMaintenance(A_LOCATION, TECHNICIAN, A_TIME);

        Mockito.verify(station).startMaintenance(TECHNICIAN, A_TIME);
    }

    @Test
    void givenTechnician_whenEndMaintenance_thenStationEndsMaintenance() {
        fleet.endMaintenance(A_LOCATION, TECHNICIAN, A_TIME);

        Mockito.verify(station).endMaintenance(TECHNICIAN, A_TIME);
    }

    @Test
    void givenTechnicianWithoutOngoingTransfer_whenStartTransfer_thenStationRetrievesScooters() {
        List<SlotNumber> slots = List.of(SLOT_1);
        Mockito.when(station.retrieveScootersForTransfer(slots)).thenReturn(List.of(scooter));

        fleet.startTransfer(TECHNICIAN, A_LOCATION, slots);

        Mockito.verify(station).retrieveScootersForTransfer(slots);
    }

    @Test
    void givenTechnicianAlreadyHasTransfer_whenStartTransfer_thenThrowsInvalidTransferException() {
        List<SlotNumber> slots = List.of(SLOT_1);
        Mockito.when(station.retrieveScootersForTransfer(slots)).thenReturn(List.of(scooter));

        fleet.startTransfer(TECHNICIAN, A_LOCATION, slots);
        Executable action = () -> fleet.startTransfer(TECHNICIAN, A_LOCATION, slots);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenUnknownTechnician_whenUnloadTransfer_thenThrowsInvalidTransferException() {
        List<SlotNumber> slots = List.of(SLOT_1);

        Executable action = () -> fleet.unloadTransfer(TECHNICIAN, A_LOCATION, slots, A_TIME);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenOngoingTransfer_whenUnloadTransfer_thenStationParksScooters() {
        List<SlotNumber> slots = List.of(SLOT_1, SLOT_2);
        List<Scooter> scooters = List.of(scooter, scooter);
        Mockito.when(transfer.unload(TECHNICIAN, 2)).thenReturn(scooters);
        fleet = new Fleet(Map.of(A_LOCATION, station), new HashMap<>(), givenOngoingTransfer());

        fleet.unloadTransfer(TECHNICIAN, A_LOCATION, slots, A_TIME);

        Mockito.verify(transfer).unload(TECHNICIAN, 2);
        Mockito.verify(station).parkScooters(slots, scooters, A_TIME);
    }

    @Test
    void givenCompletedTransfer_whenUnloadTransfer_thenTransferIsRemoved() {
        List<SlotNumber> slots = List.of(SLOT_1);
        List<Scooter> scooters = List.of(scooter);
        Mockito.when(transfer.unload(TECHNICIAN, 1)).thenReturn(scooters);
        Mockito.when(transfer.isCompleted()).thenReturn(true);
        Map<Idul, Transfer> transfers = givenOngoingTransfer();
        fleet = new Fleet(Map.of(A_LOCATION, station), new HashMap<>(), transfers);

        fleet.unloadTransfer(TECHNICIAN, A_LOCATION, slots, A_TIME);

        Assertions.assertFalse(transfers.containsKey(TECHNICIAN));
    }

    @Test
    void givenUncompletedTransfer_whenUnloadTransfer_thenTransferRemainsInMap() {
        List<SlotNumber> slots = List.of(SLOT_1);
        List<Scooter> scooters = List.of(scooter);
        Mockito.when(transfer.unload(TECHNICIAN, 1)).thenReturn(scooters);
        Mockito.when(transfer.isCompleted()).thenReturn(false);
        Map<Idul, Transfer> transfers = givenOngoingTransfer();
        fleet = new Fleet(Map.of(A_LOCATION, station), new HashMap<>(), transfers);

        fleet.unloadTransfer(TECHNICIAN, A_LOCATION, slots, A_TIME);

        Assertions.assertTrue(transfers.containsKey(TECHNICIAN));
    }

    private Map<Idul, Transfer> givenOngoingTransfer() {
        Map<Idul, Transfer> transfers = new HashMap<>();
        transfers.put(TECHNICIAN, transfer);
        return transfers;
    }
}
