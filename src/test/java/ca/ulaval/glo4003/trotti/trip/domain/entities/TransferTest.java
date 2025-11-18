package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class TransferTest {

    private static final TransferId TRANSFER_ID = TransferId.randomId();
    private static final Location SOURCE_STATION = Location.of("Building A");
    private static final Idul TECHNICIAN_ID = Idul.from("tech123");

    private Transfer transfer;

    @Mock
    private Station mockStation;

    @BeforeEach
    void setup() {
        mockStation = Mockito.mock(Station.class);
        transfer = new Transfer(TRANSFER_ID, SOURCE_STATION, TECHNICIAN_ID);
    }

    @Test
    void givenTransferWithTwoScooters_whenTryToUnloadThree_thenThrowsException() {
        transfer.getScooters().put(ScooterId.randomId(), false);
        transfer.getScooters().put(ScooterId.randomId(), false);
        List<SlotNumber> slots = List.of(new SlotNumber(1), new SlotNumber(2), new SlotNumber(3));

        Assertions.assertThrows(InsufficientScootersInTransitException.class,
                () -> transfer.unload(mockStation, slots));
    }

    @Test
    void givenTransferWithScooters_whenUnload_thenScootersMarkedAsCompleted() {
        ScooterId scooterId1 = ScooterId.randomId();
        ScooterId scooterId2 = ScooterId.randomId();
        transfer.getScooters().put(scooterId1, false);
        transfer.getScooters().put(scooterId2, false);
        List<SlotNumber> slots = List.of(new SlotNumber(1), new SlotNumber(2));

        transfer.unload(mockStation, slots);

        Assertions.assertTrue(transfer.getScooters().get(scooterId1));
        Assertions.assertTrue(transfer.getScooters().get(scooterId2));
    }
}
