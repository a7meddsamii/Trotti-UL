package ca.ulaval.glo4003.trotti.domain.trip.store;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.util.Optional;

public interface UnlockCodeStore {

    void store(UnlockCode unlockCode);

    void revoke(Idul travelerId);

    Optional<UnlockCode> getByTravelerId(Idul travelerId);

    boolean isAlive(String codeValue);
}
