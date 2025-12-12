package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.TransferPersistenceMapper;
import java.util.Map;

public class InMemoryTransferRepository {
    private final Map<TransferId, Transfer> transfers;
    private final TransferPersistenceMapper transferPersistenceMapper;

    public InMemoryTransferRepository(
            Map<TransferId, Transfer> transfers,
            TransferPersistenceMapper transferPersistenceMapper) {
        this.transfers = transfers;
        this.transferPersistenceMapper = transferPersistenceMapper;
    }

    public void save(Transfer transfer) {
        transfers.put(transfer.getTransferId(), transfer);
    }

    public Transfer findById(TransferId transferId) {
        return transfers.get(transferId);
    }
}
