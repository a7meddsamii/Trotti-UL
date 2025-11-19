package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;
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

        Transfer result = repository.findById(A_TRANSFER_ID);

        Mockito.verify(mapper).toRecord(transfer);
        Assertions.assertEquals(transfer, result);
    }

    @Test
    void givenNoTransferSaved_whenFindById_thenReturnNull() {
        Transfer result = repository.findById(A_TRANSFER_ID);

        Assertions.assertNull(result);
    }
}
