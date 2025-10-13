package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;

import java.util.Optional;

public class UnlockCodeService {

    private final UnlockCodeStore unlockCodeStore;

    public UnlockCodeService(UnlockCodeStore unlockCodeStore) {
        this.unlockCodeStore = unlockCodeStore;
    }

    public UnlockCode requestUnlockCode(Id passId) {
        Optional<UnlockCode> existingUnlockCode = unlockCodeStore.getByRidePermitId(passId);
        if (existingUnlockCode.isPresent()) {
            return existingUnlockCode.get();
        }

        UnlockCode unlockCode = UnlockCode.generateFromRidePermit(passId);
        unlockCodeStore.store(unlockCode);

        return unlockCode;
    }
}
