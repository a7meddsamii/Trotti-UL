package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.TransferRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryTransferRepositoryTest {

    public TransferId transferId;
    private Map<TransferId, TransferRecord> transfers;
    private TransferPersistenceMapper transferPersistenceMapper;
    private Transfer transfer;
    private TransferRecord transferRecord;

    private TransferRepository transferRepository;

    @BeforeEach
    void setup() {
        transferId = Mockito.mock(TransferId.class);
        transfer = Mockito.mock(Transfer.class);
        transferRecord = Mockito.mock(TransferRecord.class);
        transfers = new HashMap<>();
        transferPersistenceMapper = Mockito.mock(TransferPersistenceMapper.class);
        transferRepository = new InMemoryTransferRepository(transfers, transferPersistenceMapper);
    }

    @Test
    void givenTransfer_whenSave_thenTransferIsSaved() {
        Mockito.when(transfer.getTransferId()).thenReturn(transferId);
        Mockito.when(transferPersistenceMapper.toRecord(transfer)).thenReturn(transferRecord);

        transferRepository.save(transfer);

        Assertions.assertTrue(transfers.containsKey(transferId));
        Assertions.assertEquals(transfers.get(transferId), transferRecord);
    }

    @Test
    void givenExistingTransferId_whenFindById_thenReturnTransfer() {
        Mockito.when(transferPersistenceMapper.toDomain(transferRecord)).thenReturn(transfer);
        transfers.put(transferId, transferRecord);

        Optional<Transfer> foundTransfer = transferRepository.findById(transferId);

        Assertions.assertTrue(foundTransfer.isPresent());
        Assertions.assertEquals(foundTransfer.get(), transfer);
    }

    @Test
    void givenNonExistingTransferId_whenFindById_thenReturnEmpty() {
        Optional<Transfer> foundTransfer = transferRepository.findById(transferId);

        Assertions.assertTrue(foundTransfer.isEmpty());
    }
}
