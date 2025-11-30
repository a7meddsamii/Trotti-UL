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
    private static final Idul ANOTHER_TECHNICIAN_ID = Idul.from("tech1231231");

    private Transfer transfer;
    private Scooter scooter1;
    private Scooter scooter2;

    @BeforeEach
    void setup() {
        scooter1 = Mockito.mock(Scooter.class);
        scooter2 = Mockito.mock(Scooter.class);

        transfer = new Transfer(TECHNICIAN_ID, new ArrayList<>(List.of(scooter1, scooter2)));
    }

    @Test
    void givenWrongTechnician_whenUnload_thenThrowsInvalidTransferException() {
        Executable action = () -> transfer.unload(ANOTHER_TECHNICIAN_ID, 1);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenTransferWithTwoScooters_whenUnloadThree_thenThrowsInvalidTransferException() {
        int unloadCount = 3;

        Executable action = () -> transfer.unload(TECHNICIAN_ID, unloadCount);

        Assertions.assertThrows(InvalidTransferException.class, action);
    }

    @Test
    void givenTransferWithTwoScooters_whenUnloadTwo_thenReturnsBothScooters() {
        List<Scooter> unloaded = transfer.unload(TECHNICIAN_ID, 2);

        Assertions.assertEquals(2, unloaded.size());
        Assertions.assertTrue(unloaded.contains(scooter1));
        Assertions.assertTrue(unloaded.contains(scooter2));
        Assertions.assertTrue(transfer.isCompleted());
    }
}
