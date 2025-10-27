package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.UnlockCodeException;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.util.Optional;

public class UnlockCodeService {

    private final UnlockCodeStore unlockCodeStore;

    public UnlockCodeService(UnlockCodeStore unlockCodeStore) {
        this.unlockCodeStore = unlockCodeStore;
    }

    public UnlockCode requestUnlockCode(Idul travelerId) {
        Optional<UnlockCode> existingUnlockCode = unlockCodeStore.getByTravelerId(travelerId);
        if (existingUnlockCode.isPresent()) {
            return existingUnlockCode.get();
        }

        UnlockCode unlockCode = UnlockCode.generateFromTravelerId(travelerId);
        unlockCodeStore.store(unlockCode);

        return unlockCode;
    }

    public void revoke(UnlockCode unlockCode) {
        Optional<UnlockCode> storedCode =
                unlockCodeStore.getByTravelerId(unlockCode.getTravelerId());

        if (storedCode.isEmpty() || !storedCode.get().equals(unlockCode)) {
            throw new UnlockCodeException("Invalid or expired unlock code");
        }

        unlockCodeStore.revoke(unlockCode.getTravelerId());
    }
}
