package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TransferTest {

    private static final Idul TECHNICIAN_ID = Idul.from("tech123");
    private static final Idul WRONG_TECHNICIAN_ID = Idul.from("tech1231231");
	private static final int AMOUNT_TO_UNLOAD = 1;

    private TransferId transferId;
    private ScooterId scooterId1;
    private ScooterId scooterId2;
    private Map<ScooterId, Boolean> scootersTransferCompletedState;
    private Transfer transfer;

    @BeforeEach
    void setup() {
        scooterId1 = ScooterId.randomId();
        scooterId2 = ScooterId.randomId();
        transferId = TransferId.randomId();
        scootersTransferCompletedState = new HashMap<>();
        scootersTransferCompletedState.put(scooterId1, false);
        scootersTransferCompletedState.put(scooterId2, false);
        transfer = new Transfer(transferId, TECHNICIAN_ID, scootersTransferCompletedState);
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
    void givenTransferAlreadyCompleted_whenUnload_thenThrowsInvalidTransferException() {
        Map<ScooterId, Boolean> scootersTransferCompletedState = new HashMap<>();
        scootersTransferCompletedState.put(scooterId1, true);
        scootersTransferCompletedState.put(scooterId2, true);
        transfer = new Transfer(transferId, TECHNICIAN_ID, scootersTransferCompletedState);

        Executable action = () -> transfer.unload(TECHNICIAN_ID, AMOUNT_TO_UNLOAD);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenAmountToUnloadGreaterThanScootersStillInTransfer_whenUnload_thenThrowsInvalidTransferException() {
        Map<ScooterId, Boolean> scootersTransferCompletedState = new HashMap<>();
        scootersTransferCompletedState.put(scooterId1, false);
        transfer = new Transfer(transferId, TECHNICIAN_ID, scootersTransferCompletedState);
        int unloadCount = scootersTransferCompletedState.size() + 1;

        Executable action = () -> transfer.unload(TECHNICIAN_ID, unloadCount);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenValidAmountOfScooterToUnload_whenUnload_thenCorrectAmountIsReturned() {
        List<ScooterId> unloaded = transfer.unload(TECHNICIAN_ID, AMOUNT_TO_UNLOAD);
        Assertions.assertEquals(AMOUNT_TO_UNLOAD, unloaded.size());
    }

    @Test
    void givenCompletedScooterDeposit_whenUnload_thenRemainingScootersAreCorrect() {
        int removedAmount = transfer.unload(TECHNICIAN_ID, 1).size();
        int expectedRemainingAmount = scootersTransferCompletedState.size() - removedAmount;

        List<ScooterId> remainingScooters = transfer.unload(TECHNICIAN_ID, expectedRemainingAmount);

        Assertions.assertEquals(expectedRemainingAmount, remainingScooters.size());
    }
}
