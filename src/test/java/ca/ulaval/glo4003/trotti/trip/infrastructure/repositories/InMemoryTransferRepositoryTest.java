package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryTransferRepositoryTest {

    private static final TransferId A_TRANSFER_ID = TransferId.randomId();

    private Transfer transfer;
    private TransferPersistenceMapper mapper;
    private InMemoryTransferRepository repository;

    @BeforeEach
    void setup() {
        transfer = Mockito.mock(Transfer.class);
        TransferRecord transferRecord = Mockito.mock(TransferRecord.class);

        Mockito.when(transfer.getTransferId()).thenReturn(A_TRANSFER_ID);
        mapper = Mockito.mock(TransferPersistenceMapper.class);

        Mockito.when(mapper.toRecord(Mockito.any())).thenReturn(transferRecord);
        Mockito.when(mapper.toDomain(Mockito.any())).thenReturn(transfer);

        repository = new InMemoryTransferRepository(mapper);
    }

    @Test
    void givenTransfer_whenSave_thenItIsStoredInRepository() {
        repository.save(transfer);

        Optional<Transfer> result = repository.findById(A_TRANSFER_ID);

        Mockito.verify(mapper).toRecord(transfer);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(transfer, result.get());
    }

    @Test
    void givenNoTransferSaved_whenFindById_thenReturnEmpty() {
        Optional<Transfer> result = repository.findById(A_TRANSFER_ID);

        Assertions.assertTrue(result.isEmpty());
    }
}
