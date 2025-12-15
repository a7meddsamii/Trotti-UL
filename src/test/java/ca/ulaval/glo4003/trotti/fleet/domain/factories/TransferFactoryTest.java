package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransferFactoryTest {

    private static final Idul TECHNICIAN_ID = Idul.from("tech123");

    private ScooterId scooterIdOne;
    private ScooterId scooterIdTwo;
    private List<ScooterId> scooterIds;

    private TransferFactory transferFactory;

    @BeforeEach
    void setup() {
        scooterIdOne = Mockito.mock(ScooterId.class);
        scooterIdTwo = Mockito.mock(ScooterId.class);
        scooterIds = List.of(scooterIdOne, scooterIdTwo);

        transferFactory = new TransferFactory();
    }

    @Test
    void givenTechnicianAndScooters_whenCreate_thenTransferIsCreatedWithTechnician() {
        Transfer transfer = transferFactory.create(TECHNICIAN_ID, scooterIds);

        Assertions.assertEquals(TECHNICIAN_ID, transfer.getTechnicianId());
    }

    @Test
    void givenScooters_whenCreate_thenAllScootersAreMarkedAsNotCompleted() {
        Transfer transfer = transferFactory.create(TECHNICIAN_ID, scooterIds);

        Map<ScooterId, Boolean> transferState = transfer.getScootersTransferCompletedState();

        Assertions.assertEquals(scooterIds.size(), transferState.size());
        Assertions.assertTrue(transferState.containsKey(scooterIdOne));
        Assertions.assertTrue(transferState.containsKey(scooterIdTwo));
        Assertions.assertFalse(transferState.get(scooterIdOne));
        Assertions.assertFalse(transferState.get(scooterIdTwo));
    }
}
