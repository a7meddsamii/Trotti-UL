package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

class ScooterSlotTest {
    private static final Id A_SCOOTER_ID = Id.randomId();
    private static final int SLOT_NUMBER = 1;
    private ScooterSlot slot;

    // @BeforeEach
    // void setup() {
    // slot = new ScooterSlot(SLOT_NUMBER);
    // }
    //
    // @Test
    // void givenEmptySlot_whenIsOccupied_thenReturnsFalse() {
    // // Assertions.assertFalse(slot.isOccupied());
    // Assertions.assertTrue(slot.getScooterId().isEmpty());
    // }
    //
    // @Test
    // void givenEmptySlot_whenDock_thenIsOccupiedIsTrue() {
    // slot.dock(A_SCOOTER_ID);
    //
    // // Assertions.assertTrue(slot.isOccupied());
    // Assertions
    // .assertTrue(slot.getScooterId().filter(id -> id.equals(A_SCOOTER_ID)).isPresent());
    // }
    //
    // @Test
    // void givenOccupiedSlot_whenUndock_thenSlotIsEmpty() {
    // slot.dock(A_SCOOTER_ID);
    //
    // slot.undock();
    //
    // // Assertions.assertFalse(slot.isOccupied());
    // Assertions.assertTrue(slot.getScooterId().isEmpty());
    // }
}
