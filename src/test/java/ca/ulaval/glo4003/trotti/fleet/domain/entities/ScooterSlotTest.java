package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class ScooterSlotTest {

    private static final SlotNumber SLOT_NUMBER = SlotNumber.from(1);

    private ScooterSlot scooterSlot;
    private Scooter scooter;

    @BeforeEach
    void setup() {
        scooterSlot = new ScooterSlot(SLOT_NUMBER);
        scooter = Mockito.mock(Scooter.class);
    }

    @Test
    void givenEmptySlot_whenDock_thenScooterIsDocked() {
        scooterSlot.dock(scooter);

        Assertions.assertTrue(scooterSlot.isOccupied());
        Assertions.assertEquals(scooter, scooterSlot.getDockedScooter());
    }

    @Test
    void givenOccupiedSlot_whenDock_thenThrowsException() {
        scooterSlot.dock(scooter);
        Scooter anotherScooter = Mockito.mock(Scooter.class);

        Executable dock = () -> scooterSlot.dock(anotherScooter);

        Assertions.assertThrows(DockingException.class, dock);
    }

    @Test
    void givenOccupiedSlot_whenUndock_thenReturnsScooterAndSlotBecomesEmpty() {
        scooterSlot.dock(scooter);

        Scooter result = scooterSlot.undock();

        Assertions.assertEquals(scooter, result);
        Assertions.assertFalse(scooterSlot.isOccupied());
    }

    @Test
    void givenEmptySlot_whenUndock_thenThrowsException() {
        Executable undock = scooterSlot::undock;

        Assertions.assertThrows(DockingException.class, undock);
    }

    @Test
    void givenScooterSlot_whenIsOccupied_thenReflectsCurrentState() {
        Assertions.assertFalse(scooterSlot.isOccupied());

        scooterSlot.dock(scooter);

        Assertions.assertTrue(scooterSlot.isOccupied());
    }
}
