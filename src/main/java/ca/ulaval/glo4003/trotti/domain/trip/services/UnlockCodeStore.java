package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;

import java.util.Optional;

public interface UnlockCodeStore {

    void store(UnlockCode unlockCode);

    void remove(UnlockCode unlockCode);

    Optional<UnlockCode> getByPassId(Id code);
}
