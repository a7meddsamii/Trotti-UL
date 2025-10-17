package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.time.Clock;
import java.util.Optional;

public class UnlockCodeService {

    private final UnlockCodeStore unlockCodeStore;
    private final Clock clock;

    public UnlockCodeService(UnlockCodeStore unlockCodeStore,
                             Clock clock) {
        this.unlockCodeStore = unlockCodeStore;
        this.clock = clock;
    }

    public UnlockCode requestUnlockCode(Id ridePermitId) {
        Optional<UnlockCode> existingUnlockCode = unlockCodeStore.getByRidePermitId(ridePermitId);
        if (existingUnlockCode.isPresent()) {
            return existingUnlockCode.get();
        }

        UnlockCode unlockCode = UnlockCode.generateFromRidePermit(ridePermitId, clock);
        unlockCodeStore.store(unlockCode);

        return unlockCode;
    }
}
