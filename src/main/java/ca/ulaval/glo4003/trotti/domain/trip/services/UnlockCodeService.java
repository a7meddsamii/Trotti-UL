package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.UnlockCodeException;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.time.Clock;
import java.util.Optional;

public class UnlockCodeService {

    private final UnlockCodeStore unlockCodeStore;
    private final Clock clock;

    public UnlockCodeService(UnlockCodeStore unlockCodeStore, Clock clock) {
        this.unlockCodeStore = unlockCodeStore;
        this.clock = clock;
    }

    public UnlockCode requestUnlockCode(Idul travelerId) {
        Optional<UnlockCode> existingUnlockCode = unlockCodeStore.getByTravelerId(travelerId);
        if (existingUnlockCode.isPresent()) {
            return existingUnlockCode.get();
        }

        UnlockCode unlockCode = UnlockCode.generateFromTravelerId(travelerId, clock);
        unlockCodeStore.store(unlockCode);

        return unlockCode;
    }

    public void validateAndRevoke(UnlockCode unlockCode, Idul travelerId) {
        validateCode(unlockCode, travelerId);
        unlockCodeStore.revoke(travelerId);
    }

    private void validateCode(UnlockCode unlockCode, Idul travelerId) {
        if (!unlockCodeStore.isValid(unlockCode, travelerId)) {
            throw new UnlockCodeException("Invalid code");
        }
    }
}
