package ca.ulaval.glo4003.trotti.fleet.domain.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;

import java.util.Optional;

public interface TransferRepository {
	
	void save(Transfer transfer);
	
	Optional<Transfer> findById(TransferId transferId);
}
