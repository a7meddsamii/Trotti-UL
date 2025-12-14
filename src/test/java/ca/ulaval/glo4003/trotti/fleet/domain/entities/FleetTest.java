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
    private static final SlotNumber SLOT_1 = SlotNumber.from(1);
    private static final SlotNumber SLOT_2 = SlotNumber.from(2);
    private static final LocalDateTime A_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final Idul TECHNICIAN = Idul.from("tech123");

    private Station station;
    private Scooter scooter;
    private Scooter secondScooter;
    private ScooterId scooterId;
    private ScooterId secondScooterId;
    private Map<ScooterId, Scooter> fleetDisplacedScootersView;
    private Fleet fleet;

    @BeforeEach
    void setup() {
        station = Mockito.mock(Station.class);
        scooter = Mockito.mock(Scooter.class);
        secondScooter = Mockito.mock(Scooter.class);
        scooterId = ScooterId.randomId();
        secondScooterId = ScooterId.randomId();
        fleetDisplacedScootersView = new HashMap<>();
        Mockito.when(scooter.getScooterId()).thenReturn(scooterId);
        Mockito.when(secondScooter.getScooterId()).thenReturn(secondScooterId);
        fleet = new Fleet(Map.of(A_LOCATION, station), fleetDisplacedScootersView);
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
    void givenValidMaintenanceInfo_whenStartMaintenance_thenStationStartsMaintenance() {
        fleet.startMaintenance(A_LOCATION, TECHNICIAN, A_TIME);

        Mockito.verify(station).startMaintenance(TECHNICIAN, A_TIME);
    }

    @Test
    void givenValidMaintenanceInfo_whenEndMaintenance_thenStationEndsMaintenance() {
        fleet.endMaintenance(A_LOCATION, TECHNICIAN, A_TIME);

        Mockito.verify(station).endMaintenance(TECHNICIAN, A_TIME);
    }

    @Test
    void givenLocationAndSlotNumbers_whenRetrieveScooters_thenReturnsScootersIds() {
        List<SlotNumber> slots = List.of(SLOT_1);
        List<ScooterId> expectedScooterIds = List.of(scooterId);
        Mockito.when(station.retrieveScootersForTransfer(slots)).thenReturn(List.of(scooter));

        List<ScooterId> retrievedScooters = fleet.retrieveScooters(A_LOCATION, slots);

        Assertions.assertEquals(expectedScooterIds, retrievedScooters);
    }

    @Test
    void givenLocationAndSlotNumbers_whenRetrieveScooters_thenPickedScootersAreDisplaced() {
        List<SlotNumber> slots = List.of(SLOT_1);
        Mockito.when(station.retrieveScootersForTransfer(slots)).thenReturn(List.of(scooter));

        fleet.retrieveScooters(A_LOCATION, slots);

        Assertions.assertTrue(fleetDisplacedScootersView.containsKey(scooterId));
    }

    @Test
    void givenMismatchedSlotsAndScooters_whenDepositScooters_thenThrowsInvalidTransferException() {
        List<SlotNumber> slots = List.of(SLOT_1);
        List<ScooterId> displacedScooterIds =
                List.of(scooter.getScooterId(), secondScooter.getScooterId());
        List<Scooter> scooters = List.of(scooter, secondScooter);
        fleet = new Fleet(Map.of(A_LOCATION, station), givenDisplacedScooters(scooters));

        Executable action =
                () -> fleet.depositScooters(A_LOCATION, slots, displacedScooterIds, A_TIME);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenOngoingTransfer_whenDepositScooters_thenStationParksScooters() {
        List<SlotNumber> slots = List.of(SLOT_1, SLOT_2);
        List<Scooter> scooters = List.of(scooter, secondScooter);
        List<ScooterId> displacedScooterIds =
                List.of(scooter.getScooterId(), secondScooter.getScooterId());
        fleet = new Fleet(Map.of(A_LOCATION, station), givenDisplacedScooters(scooters));

        fleet.depositScooters(A_LOCATION, slots, displacedScooterIds, A_TIME);

        Mockito.verify(station).parkScooters(slots, scooters, A_TIME);
    }

    @Test
    void givenDisplacedScooters_whenDepositScooters_thenDisplacedScootersAreRemoved() {
        List<SlotNumber> slots = List.of(SLOT_1, SLOT_2);
        List<Scooter> scooters = List.of(scooter, secondScooter);
        List<ScooterId> displacedScooterIds =
                List.of(scooter.getScooterId(), secondScooter.getScooterId());
        fleet = new Fleet(Map.of(A_LOCATION, station), givenDisplacedScooters(scooters));

        fleet.depositScooters(A_LOCATION, slots, displacedScooterIds, A_TIME);

        Assertions.assertFalse(fleetDisplacedScootersView.containsKey(scooterId));
        Assertions.assertFalse(fleetDisplacedScootersView.containsKey(secondScooterId));
    }

    @Test
    void givenScooterNotCurrentlyDisplaced_whenDepositScooters_thenThrowsInvalidTransferException() {
        List<SlotNumber> slots = List.of(SLOT_1);
        List<ScooterId> displacedScooterIds = List.of(scooterId);

        Executable action =
                () -> fleet.depositScooters(A_LOCATION, slots, displacedScooterIds, A_TIME);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    private Map<ScooterId, Scooter> givenDisplacedScooters(List<Scooter> scooters) {
        Map<ScooterId, Scooter> displacedScooters = new HashMap<>();

        for (Scooter scooter : scooters) {
            displacedScooters.put(scooter.getScooterId(), scooter);
        }

        return displacedScooters;
    }

    @Test
    void givenLocation_whenGetAvailableSlots_thenReturnsStationAvailableSlots() {
        List<SlotNumber> availableSlots = List.of(SLOT_1, SLOT_2);
        Mockito.when(station.getAvailableSlots()).thenReturn(availableSlots);

        List<SlotNumber> result = fleet.getAvailableSlots(A_LOCATION);

        Assertions.assertEquals(availableSlots, result);
        Mockito.verify(station).getAvailableSlots();
    }

    @Test
    void givenLocation_whenGetOccupiedSlots_thenReturnsStationOccupiedSlots() {
        List<SlotNumber> occupiedSlots = List.of(SLOT_1);
        Mockito.when(station.getOccupiedSlots()).thenReturn(occupiedSlots);

        List<SlotNumber> result = fleet.getOccupiedSlots(A_LOCATION);

        Assertions.assertEquals(occupiedSlots, result);
        Mockito.verify(station).getOccupiedSlots();
    }

}
