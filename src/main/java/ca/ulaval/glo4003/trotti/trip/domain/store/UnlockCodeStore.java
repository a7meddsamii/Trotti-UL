package ca.ulaval.glo4003.trotti.trip.domain.store;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.time.Clock;

public interface UnlockCodeStore {

    UnlockCode generateOrGet(Idul idul, RidePermitId ridePermitId, Clock clock);

    void revoke(Idul idul, RidePermitId ridePermitId);

    void validate(Idul idul, RidePermitId ridePermitId, String unlockCode);
}
