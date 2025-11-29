package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;

/**
 * @deprecated will reimplement when transfer persistence is needed
 *
 */
public class TransferPersistenceMapper {

    public TransferRecord toRecord(Transfer transfer) {
        return null;
	}
	
    public Transfer toDomain(TransferRecord record) {
        return null;
    }
}
