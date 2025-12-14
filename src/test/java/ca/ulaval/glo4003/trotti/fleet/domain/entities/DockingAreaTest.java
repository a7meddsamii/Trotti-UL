package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.DockingException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class DockingAreaTest {

    private static final SlotNumber SLOT_1 = new SlotNumber(1);
    private static final SlotNumber SLOT_2 = new SlotNumber(2);
    private static final SlotNumber INVALID_SLOT = new SlotNumber(99);
    private static final LocalDateTime A_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);

    private DockingArea dockingArea;
    private ScooterSlot slot1;
    private ScooterSlot slot2;
    private Scooter scooter;

    @BeforeEach
    void setup() {
        slot1 = Mockito.mock(ScooterSlot.class);
        slot2 = Mockito.mock(ScooterSlot.class);
        scooter = Mockito.mock(Scooter.class);

        Map<SlotNumber, ScooterSlot> slots = new HashMap<>();
        slots.put(SLOT_1, slot1);
        slots.put(SLOT_2, slot2);

        dockingArea = new DockingArea(slots);
    }

    @Test
    void givenValidSlot_whenDock_thenScooterIsDocked() {
        dockingArea.dock(SLOT_1, scooter);

        Mockito.verify(slot1).dock(scooter);
    }

    @Test
    void givenInvalidSlot_whenDock_thenThrowsException() {
        Executable dock = () -> dockingArea.dock(INVALID_SLOT, scooter);

        Assertions.assertThrows(DockingException.class, dock);
    }

    @Test
    void givenValidSlot_whenUndock_thenReturnsScooter() {
        Mockito.when(slot1.undock()).thenReturn(scooter);

        Scooter result = dockingArea.undock(SLOT_1);

        Assertions.assertEquals(scooter, result);
        Mockito.verify(slot1).undock();
    }

    @Test
    void givenOccupiedSlot_whenFindOccupiedSlots_thenReturnsSlotList() {
        Mockito.when(slot1.isOccupied()).thenReturn(true);
        Mockito.when(slot2.isOccupied()).thenReturn(false);

        List<SlotNumber> result = dockingArea.findOccupiedSlots();

        Assertions.assertEquals(List.of(SLOT_1), result);
    }

    @Test
    void givenAvailableSlot_whenFindAvailableSlots_thenReturnsSlotList() {
        Mockito.when(slot1.isOccupied()).thenReturn(false);
        Mockito.when(slot2.isOccupied()).thenReturn(true);

        List<SlotNumber> result = dockingArea.findAvailableSlots();

        Assertions.assertEquals(List.of(SLOT_1), result);
    }

    @Test
    void givenOccupiedSlots_whenTurnOffElectricity_thenScootersPauseCharging() {
        Mockito.when(slot1.isOccupied()).thenReturn(true);
        Mockito.when(slot2.isOccupied()).thenReturn(true);
        Mockito.when(slot1.getDockedScooter()).thenReturn(scooter);
        Mockito.when(slot2.getDockedScooter()).thenReturn(scooter);

        dockingArea.turnOffElectricity(A_TIME);

        Mockito.verify(scooter, Mockito.times(2)).pauseCharging(A_TIME);
    }

    @Test
    void givenOccupiedSlots_whenTurnOnElectricity_thenScootersResumeCharging() {
        Mockito.when(slot1.isOccupied()).thenReturn(true);
        Mockito.when(slot2.isOccupied()).thenReturn(true);
        Mockito.when(slot1.getDockedScooter()).thenReturn(scooter);
        Mockito.when(slot2.getDockedScooter()).thenReturn(scooter);

        dockingArea.turnOnElectricity(A_TIME);

        Mockito.verify(scooter, Mockito.times(2)).resumeCharging(A_TIME);
    }
}
