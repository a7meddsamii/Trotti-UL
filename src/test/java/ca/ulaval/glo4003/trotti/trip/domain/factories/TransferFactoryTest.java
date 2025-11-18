package ca.ulaval.glo4003.trotti.trip.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TransferFactoryTest {

    private static final Location SOURCE_STATION = Location.of("Building A");
    private static final Idul TECHNICIAN_ID = Idul.from("tech123");
    private static final List<SlotNumber> SOURCE_SLOTS = List.of(new SlotNumber(1), new SlotNumber(2));

    private TransferFactory transferFactory;

    @Mock
    private Station mockStation;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        transferFactory = new TransferFactory();
    }

    @Test
    void whenCreateTransfer_thenReturnsTransferWithCorrectAttributes() {
        ScooterId scooterId1 = ScooterId.randomId();
        ScooterId scooterId2 = ScooterId.randomId();
        Mockito.when(mockStation.getScooter(new SlotNumber(1))).thenReturn(scooterId1);
        Mockito.when(mockStation.getScooter(new SlotNumber(2))).thenReturn(scooterId2);

        Mockito.when(mockStation.getLocation()).thenReturn(SOURCE_STATION);
        
        Transfer transfer = transferFactory.createTransfer(TECHNICIAN_ID, mockStation, SOURCE_SLOTS);

        Assertions.assertEquals(SOURCE_STATION, transfer.getSourceStation());
        Assertions.assertEquals(TECHNICIAN_ID, transfer.getTechnicianId());
        Assertions.assertNotNull(transfer.getTransferId());
    }

    @Test
    void whenCreateTransfer_thenLoadsScootersFromSlots() {
        ScooterId scooterId1 = ScooterId.randomId();
        ScooterId scooterId2 = ScooterId.randomId();
        Mockito.when(mockStation.getScooter(new SlotNumber(1))).thenReturn(scooterId1);
        Mockito.when(mockStation.getScooter(new SlotNumber(2))).thenReturn(scooterId2);

        Transfer transfer = transferFactory.createTransfer(TECHNICIAN_ID, mockStation, SOURCE_SLOTS);

        Assertions.assertEquals(2, transfer.getScooters().size());
        Assertions.assertTrue(transfer.getScooters().containsKey(scooterId1));
        Assertions.assertTrue(transfer.getScooters().containsKey(scooterId2));
        Assertions.assertFalse(transfer.getScooters().get(scooterId1));
        Assertions.assertFalse(transfer.getScooters().get(scooterId2));
    }
}