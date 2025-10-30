package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ScooterSlotTest {
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private ScooterSlot slot;

    @BeforeEach
    void setup() {
        slot = new ScooterSlot(SLOT_NUMBER);
    }

    @Test
    void givenEmptySlot_whenDock_thenSucceeds() {
        Executable dock = () -> slot.dock(A_SCOOTER_ID);

        Assertions.assertDoesNotThrow(dock);
        Assertions.assertEquals(A_SCOOTER_ID, slot.undock());
    }

    @Test
    void givenOccupiedSlot_whenDock_thenThrowsException() {
        slot.dock(A_SCOOTER_ID);

        Executable dock = () -> slot.dock(A_SCOOTER_ID);

        Assertions.assertThrows(DockingException.class, dock);
    }

    @Test
    void givenOccupiedSlot_whenUndock_thenSucceeds() {
        slot.dock(A_SCOOTER_ID);

        Executable undock = () -> slot.undock();

        Assertions.assertDoesNotThrow(undock);
        Assertions.assertDoesNotThrow(() -> slot.dock(A_SCOOTER_ID));
    }

    @Test
    void givenEmptySlot_whenUndock_thenThrowsException() {
        Executable undock = () -> slot.undock();

        Assertions.assertThrows(DockingException.class, undock);
    }
}
