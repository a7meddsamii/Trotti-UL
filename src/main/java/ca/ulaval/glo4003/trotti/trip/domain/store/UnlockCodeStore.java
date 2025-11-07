package ca.ulaval.glo4003.trotti.trip.domain.store;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import java.util.Optional;

public interface UnlockCodeStore {

    void store(UnlockCode unlockCode);

    void revoke(Idul travelerId);

    Optional<UnlockCode> getByTravelerId(Idul travelerId);

}
