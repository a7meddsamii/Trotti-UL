package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records.TransferRecord;
import java.util.HashMap;
import java.util.Map;

public class TransferPersistenceMapper {

    public Transfer toDomain(TransferRecord transferRecord) {
        Map<ScooterId, Boolean> scootersTransferCompletedState =
                new HashMap<>(transferRecord.scootersTransferCompletedState());

        return new Transfer(transferRecord.transferId(), transferRecord.technicianId(),
                scootersTransferCompletedState);
    }

    public TransferRecord toRecord(Transfer transfer) {
        Map<ScooterId, Boolean> scootersTransferCompletedState =
                new HashMap<>(transfer.getScootersTransferCompletedState());

        return new TransferRecord(transfer.getTransferId(), transfer.getTechnicianId(),
                scootersTransferCompletedState);
    }
}
