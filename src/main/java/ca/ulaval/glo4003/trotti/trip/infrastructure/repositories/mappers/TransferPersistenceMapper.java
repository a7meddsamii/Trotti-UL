package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TransferRecord;
import java.util.HashMap;

public class TransferPersistenceMapper {

    public TransferRecord toRecord(Transfer transfer) {
        return new TransferRecord(transfer.getTransferId(), transfer.getSourceStation(),
                transfer.getTechnicianId(), transfer.getScootersMovedCopy());
    }

    public Transfer toDomain(TransferRecord record) {
        return Transfer.rehydrate(
                TransferId.from(record.transferId().toString()),
                Location.of(record.location().toString()),
                Idul.from(record.technicianId().toString()),
                new HashMap<>(record.scootersMoved())
        );
    }
}
