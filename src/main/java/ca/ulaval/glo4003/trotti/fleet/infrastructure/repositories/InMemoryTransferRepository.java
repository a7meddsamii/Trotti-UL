package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers.TransferPersistenceMapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.TransferRecord;

import java.util.Map;
import java.util.Optional;

public class InMemoryTransferRepository implements TransferRepository {
	private final Map<TransferId, TransferRecord> transfers;
	private final TransferPersistenceMapper transferPersistenceMapper;
	
	public InMemoryTransferRepository(Map<TransferId, TransferRecord> transfers, TransferPersistenceMapper transferPersistenceMapper) {
		this.transfers = transfers;
		this.transferPersistenceMapper = transferPersistenceMapper;
	}
	
	@Override
	public void save(Transfer transfer) {
		transfers.put(transfer.getTransferId(), transferPersistenceMapper.toRecord(transfer));
	}
	
	@Override
	public Optional<Transfer> findById(TransferId transferId) {
		return Optional.ofNullable(transfers.get(transferId))
				.map(transferPersistenceMapper::toDomain);
	}
}
