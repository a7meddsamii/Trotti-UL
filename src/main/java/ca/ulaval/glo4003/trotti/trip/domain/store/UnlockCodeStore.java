package ca.ulaval.glo4003.trotti.trip.domain.store;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import java.time.Clock;

public interface UnlockCodeStore {

    UnlockCode get(Idul idul, RidePermitId ridePermitId, Clock clock);

    void revoke(Idul idul, RidePermitId ridePermitId);

    void validate(Idul idul, RidePermitId ridePermitId, String unlockCode);
}
