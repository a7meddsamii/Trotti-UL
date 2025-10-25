package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;

import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
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

//    public void validateAndRetrieveUnlockCode(String codeValue, Traveler traveler) throws ScooterUnlockException {
//        unlockCodeStore.isAlive(codeValue);
//        validateCodeBelongsToTraveler(unlockCode, traveler);
//    }

//    private void validateCodeBelongsToTraveler(UnlockCode unlockCode, Traveler traveler) {
//        if (!traveler.walletHasPermit(unlockCode.getTravelerId())) {
//            throw new ScooterUnlockException(
//                    "Unlock code does not match any active ride permit for this traveler");
//        }
//    }
}
