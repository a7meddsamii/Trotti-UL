package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;

public class TransferPersistenceMapper {

    public TransferRecord toRecord(Transfer transfer) {
        return new TransferRecord(transfer.getTransferId(), transfer.getSourceStation(),
                transfer.getTechnicianId(), transfer.getScootersMovedCopy());
    }

    public Transfer toDomain(TransferRecord record) {
        return Transfer.rehydrate(record.transferId(), record.location(), record.technicianId(),
                record.scootersMoved());
    }
}
