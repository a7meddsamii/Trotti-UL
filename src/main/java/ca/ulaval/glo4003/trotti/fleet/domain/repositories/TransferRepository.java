package ca.ulaval.glo4003.trotti.fleet.domain.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;

public interface TransferRepository {
	Transfer findById(TransferId transferId);
}
