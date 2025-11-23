package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.Optional;

public interface TransferRepository {
    void save(Transfer transfer);

    Optional<Transfer> findById(TransferId transferId);
}
