package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferTest {
    private Transfer transfer;
    private TransferId transferId;
    private Location sourceLocation;
    private List<SlotNumber> sourceSlots;

    @BeforeEach
    void setup() {
        transferId = TransferId.randomId();
        sourceLocation = Location.of("Building A");
        sourceSlots = List.of(new SlotNumber(1), new SlotNumber(2));
        transfer = new Transfer(transferId, sourceLocation, sourceSlots);
    }

    @Test
    void givenNewTransfer_whenCreated_thenHasNoScootersInTransit() {
        Assertions.assertEquals(0, transfer.getRemainingScooterCount());
    }

    @Test
    void givenTransfer_whenAddScootersToTransit_thenScootersAreInTransit() {
        List<ScooterId> scooterIds = List.of(ScooterId.randomId(), ScooterId.randomId());
        
        transfer.addScootersToTransit(scooterIds);
        
        Assertions.assertEquals(2, transfer.getRemainingScooterCount());
        Assertions.assertTrue(transfer.getScootersInTransit().containsAll(scooterIds));
    }

    @Test
    void givenTransferWithScootersInTransit_whenRemoveAllScooters_thenNoScootersRemain() {
        List<ScooterId> scooterIds = List.of(ScooterId.randomId(), ScooterId.randomId());
        transfer.addScootersToTransit(scooterIds);
        
        List<ScooterId> removedScooters = transfer.removeScootersFromTransit(2);
        
        Assertions.assertEquals(2, removedScooters.size());
        Assertions.assertEquals(0, transfer.getRemainingScooterCount());
    }

    @Test
    void givenTransferWithTwoScooters_whenTryToRemoveThree_thenThrowsException() {
        List<ScooterId> scooterIds = List.of(ScooterId.randomId(), ScooterId.randomId());
        transfer.addScootersToTransit(scooterIds);
        
        Assertions.assertThrows(InsufficientScootersInTransitException.class,
                () -> transfer.removeScootersFromTransit(3));
    }
}