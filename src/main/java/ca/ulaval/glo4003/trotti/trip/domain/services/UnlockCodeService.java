package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.UnlockCodeException;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import java.util.Optional;

@Deprecated
public class UnlockCodeService {

    private final UnlockCodeStore unlockCodeStore;

    public UnlockCodeService(UnlockCodeStore unlockCodeStore) {
        this.unlockCodeStore = unlockCodeStore;
    }

    public UnlockCode requestUnlockCode(Idul travelerId) {
        return null;
    }

    public void revoke(UnlockCode unlockCode) {
        return;
    }
}
