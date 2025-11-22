package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class DockingAreaTest {

    private static final int CAPACITY = 3;
    private static final SlotNumber VALID_SLOT = new SlotNumber(1);
    private static final SlotNumber INVALID_SLOT = new SlotNumber(99);
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private DockingArea dockingArea;

    @BeforeEach
    void setup() {
        Map<SlotNumber, Optional<ScooterId>> slots = new HashMap<>();
        for (int i = 1; i <= CAPACITY; i++) {
            slots.put(new SlotNumber(i), Optional.empty());
        }
        dockingArea = new DockingArea(slots);
    }

    @Test
    void givenValidSlot_whenDock_thenDocksScooter() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        Assertions.assertTrue(dockingArea.getScooterSlots().get(VALID_SLOT).isPresent());
        Assertions.assertEquals(A_SCOOTER_ID, dockingArea.getScooterSlots().get(VALID_SLOT).get());
    }

    @Test
    void givenInvalidSlot_whenDock_thenThrowsDockingException() {
        Executable dock = () -> dockingArea.dock(INVALID_SLOT, A_SCOOTER_ID);

        Assertions.assertThrows(DockingException.class, dock);
    }

    @Test
    void givenValidSlot_whenUndock_thenUndocksAndReturnsScooterId() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        ScooterId result = dockingArea.undock(VALID_SLOT);

        Assertions.assertEquals(A_SCOOTER_ID, result);
        Assertions.assertTrue(dockingArea.getScooterSlots().get(VALID_SLOT).isEmpty());
    }

    @Test
    void givenInvalidSlot_whenUndock_thenThrowsDockingException() {
        Executable undock = () -> dockingArea.undock(INVALID_SLOT);

        Assertions.assertThrows(DockingException.class, undock);
    }

    @Test
    void givenOccupiedSlot_whenFindOccupiedSlots_thenReturnsThatSlot() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        List<SlotNumber> occupiedSlots = dockingArea.findOccupiedSlots();

        Assertions.assertEquals(1, occupiedSlots.size());
        Assertions.assertTrue(occupiedSlots.contains(VALID_SLOT));
    }

    @Test
    void givenAvailableSlot_whenFindAvailableSlots_thenReturnsThatSlot() {
        List<SlotNumber> availableSlots = dockingArea.findAvailableSlots();

        Assertions.assertEquals(CAPACITY, availableSlots.size());
        Assertions.assertTrue(availableSlots.contains(VALID_SLOT));
    }

    @Test
    void givenScooterSlots_whenGetAllScooterIds_thenReturnsAllScooterIds() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        List<ScooterId> result = dockingArea.getAllScooterIds();

        Assertions.assertEquals(List.of(A_SCOOTER_ID), result);
    }

    @Test
    void givenOccupiedSlot_whenDock_thenThrowsDockingException() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        Executable dock = () -> dockingArea.dock(VALID_SLOT, ScooterId.randomId());

        Assertions.assertThrows(DockingException.class, dock);
    }

    @Test
    void givenEmptySlot_whenUndock_thenThrowsDockingException() {
        Executable undock = () -> dockingArea.undock(VALID_SLOT);

        Assertions.assertThrows(DockingException.class, undock);
    }
}
