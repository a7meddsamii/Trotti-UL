package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTransferRepository implements TransferRepository {

    private final Map<TransferId, TransferRecord> transfers = new HashMap<>();
    private final TransferPersistenceMapper mapper;

    public InMemoryTransferRepository(TransferPersistenceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void save(Transfer transfer) {
        TransferRecord record = mapper.toRecord(transfer);
        transfers.put(transfer.getTransferId(), record);
    }

    @Override
    public Transfer findById(TransferId transferId) {
        TransferRecord record = transfers.get(transferId);
        return record == null ? null : mapper.toDomain(record);
    }
}
