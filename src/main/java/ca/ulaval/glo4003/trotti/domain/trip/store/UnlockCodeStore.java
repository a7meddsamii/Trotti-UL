package ca.ulaval.glo4003.trotti.domain.trip.store;

import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.util.Optional;

public interface UnlockCodeStore {

    void store(UnlockCode unlockCode);

    void revoke(RidePermitId ridePermitId);

    Optional<UnlockCode> getByRidePermitId(RidePermitId ridePermitId);
}
