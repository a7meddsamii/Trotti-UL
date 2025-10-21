package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class DockingAreaTest {
    private static final int VALID_SLOT = 1;
    private static final int INVALID_SLOT = -1;
    private static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    private ScooterSlot A_SCOOTER_SLOT;
    private DockingArea dockingArea;

    @BeforeEach
    void setup() {
        A_SCOOTER_SLOT = Mockito.mock(ScooterSlot.class);
        Map<Integer, ScooterSlot> scooterSlots = new HashMap<>();
        scooterSlots.put(VALID_SLOT, A_SCOOTER_SLOT);
        dockingArea = new DockingArea(scooterSlots);
    }

    @Test
    void givenValidSlot_whenDock_thenDelegatesToScooterSlot() {
        dockingArea.dock(VALID_SLOT, A_SCOOTER_ID);

        Mockito.verify(A_SCOOTER_SLOT).dock(A_SCOOTER_ID);
    }

    @Test
    void givenInvalidSlot_whenDock_thenThrowsDockingException() {
        Executable dock = () -> dockingArea.dock(INVALID_SLOT, A_SCOOTER_ID);

        Assertions.assertThrows(DockingException.class, dock);
        Mockito.verifyNoInteractions(A_SCOOTER_SLOT);
    }

    @Test
    void givenValidSlot_whenUndock_thenDelegatesToScooterSlotAndReturnsScooterId() {
        Mockito.when(A_SCOOTER_SLOT.undock()).thenReturn(A_SCOOTER_ID);

        Id result = dockingArea.undock(VALID_SLOT);

        Assertions.assertEquals(A_SCOOTER_ID, result);
        Mockito.verify(A_SCOOTER_SLOT).undock();
    }

    @Test
    void givenInvalidSlot_whenUndock_thenThrowsDockingException() {
        Executable undock = () -> dockingArea.undock(INVALID_SLOT);

        Assertions.assertThrows(DockingException.class, undock);
        Mockito.verifyNoInteractions(A_SCOOTER_SLOT);
    }
}
