package ca.ulaval.glo4003.trotti.domain.trip.store;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import java.util.Optional;

public interface UnlockCodeStore {

    void store(UnlockCode unlockCode);

    void revoke(Id ridePermitId);

    Optional<UnlockCode> getByRidePermitId(Id ridePermitId);
}
