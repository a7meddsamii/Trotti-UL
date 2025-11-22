package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferPersistenceMapperTest {

    private static final Idul TECHNICIAN_ID = Idul.from("abcv521d");
    private static final Location SOURCE_LOCATION = Location.of("Pavillon Vachon", "Station-1");
    private static final ScooterId SCOOTER_1 = ScooterId.randomId();
    private static final ScooterId SCOOTER_2 = ScooterId.randomId();

    private TransferPersistenceMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new TransferPersistenceMapper();
    }

    @Test
    void givenTransfer_whenToRecord_thenReturnEquivalentRecord() {
        Transfer transfer =
                Transfer.start(TECHNICIAN_ID, SOURCE_LOCATION, Set.of(SCOOTER_1, SCOOTER_2));

        TransferRecord record = mapper.toRecord(transfer);

        Assertions.assertEquals(transfer.getTransferId(), record.transferId());
        Assertions.assertEquals(transfer.getSourceStation(), record.location());
        Assertions.assertEquals(transfer.getTechnicianId(), record.technicianId());
        Assertions.assertEquals(transfer.getScootersMovedCopy(), record.scootersMoved());
    }

    @Test
    void givenTransferRecord_whenToDomain_thenReturnEquivalentDomainTransfer() {
        TransferId transferId = TransferId.randomId();
        Map<ScooterId, Boolean> moved = Map.of(SCOOTER_1, false, SCOOTER_2, true);
        TransferRecord record =
                new TransferRecord(transferId, SOURCE_LOCATION, TECHNICIAN_ID, moved);

        Transfer transfer = mapper.toDomain(record);

        Assertions.assertEquals(record.transferId(), transfer.getTransferId());
        Assertions.assertEquals(record.location(), transfer.getSourceStation());
        Assertions.assertEquals(record.technicianId(), transfer.getTechnicianId());
        Assertions.assertEquals(record.scootersMoved(), transfer.getScootersMovedCopy());
    }
}
