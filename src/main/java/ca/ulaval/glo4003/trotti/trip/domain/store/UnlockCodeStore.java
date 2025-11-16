package ca.ulaval.glo4003.trotti.trip.domain.store;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.UnlockCodeKey;

public interface UnlockCodeStore {

    UnlockCode generate(Idul idul, RidePermitId ridePermitId);

    void revoke(UnlockCodeKey unlockCodeKey);

    void validate(UnlockCode unlockCode, RidePermitId ridePermitId);
}
