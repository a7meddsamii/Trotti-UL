package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class TransferTest {

    private static final Idul TECHNICIAN_ID = Idul.from("tech123");
    private static final Idul WRONG_TECHNICIAN_ID = Idul.from("tech1231231");
    public static final int AMOUNT_TO_UNLOAD = 1;

    private Transfer transfer;

    @BeforeEach
    void setup() {
        Scooter scooter1 = Mockito.mock(Scooter.class);
        Scooter scooter2 = Mockito.mock(Scooter.class);

        transfer = new Transfer(TECHNICIAN_ID, new ArrayList<>(List.of(scooter1, scooter2)));
    }

    @Test
    void givenWrongTechnician_whenUnload_thenThrowsInvalidTransferException() {
        Executable action = () -> transfer.unload(WRONG_TECHNICIAN_ID, AMOUNT_TO_UNLOAD);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenAmountToUnloadLessThanOne_whenUnload_thenThrowsInvalidTransferException() {
        int unloadCount = 0;

        Executable action = () -> transfer.unload(TECHNICIAN_ID, unloadCount);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenAmountToUnloadGreaterThanScootersStillInTransfer_whenUnload_thenThrowsInvalidTransferException() {
        int unloadCount = transfer.getScootersToMove().size() + 1;

        Executable action = () -> transfer.unload(TECHNICIAN_ID, unloadCount);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenValidAmountOfScooterToUnload_whenUnload_thenCorrectAmountIsRemoved() {
        int expectedRemainingScooters = transfer.getScootersToMove().size() - AMOUNT_TO_UNLOAD;

        List<Scooter> unloaded = transfer.unload(TECHNICIAN_ID, AMOUNT_TO_UNLOAD);

        Assertions.assertEquals(AMOUNT_TO_UNLOAD, unloaded.size());
        Assertions.assertEquals(expectedRemainingScooters, transfer.getScootersToMove().size());
    }

    @Test
    void givenAllScootersUnloaded_whenIsCompleted_thenReturnsTrue() {
        int scootersToUnload = transfer.getScootersToMove().size();

        transfer.unload(TECHNICIAN_ID, scootersToUnload);

        Assertions.assertTrue(transfer.isCompleted());
    }

    @Test
    void givenNoRemainingScooters_whenUnload_thenThrowsInvalidTransferException() {
        int scootersToUnload = transfer.getScootersToMove().size();
        transfer.unload(TECHNICIAN_ID, scootersToUnload);

        Executable action = () -> transfer.unload(TECHNICIAN_ID, AMOUNT_TO_UNLOAD);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }
}
