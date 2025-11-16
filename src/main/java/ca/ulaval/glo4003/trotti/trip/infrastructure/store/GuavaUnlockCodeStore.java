package ca.ulaval.glo4003.trotti.trip.infrastructure.store;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.UnlockCodeKey;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class GuavaUnlockCodeStore implements UnlockCodeStore {

    private static final long DEFAULT_MAX_SIZE = 1000;
    private static final long DEFAULT_EXPIRATION_SECONDS = 60;

    private final Cache<UnlockCodeKey, UnlockCode> codeCache;

    public GuavaUnlockCodeStore() {
        this.codeCache = CacheBuilder.newBuilder().maximumSize(DEFAULT_MAX_SIZE)
                .expireAfterWrite(DEFAULT_EXPIRATION_SECONDS, TimeUnit.SECONDS).recordStats()
                .build();
    }

    @Override
    public UnlockCode generate(Idul idul, RidePermitId ridePermitId) {
        UnlockCode unlockCode = UnlockCode.generateFromTravelerId(idul);
        codeCache.put(UnlockCodeKey.from(idul, ridePermitId), unlockCode);
        return unlockCode;
    }

    @Override
    public void revoke(UnlockCodeKey unlockCodeKey) {
        codeCache.invalidate(unlockCodeKey);
    }

    @Override
    public void validate(UnlockCode unlockCode, RidePermitId ridePermitId) {
        UnlockCode storedUnlockCode = codeCache.getIfPresent(UnlockCodeKey.from(unlockCode.getTravelerId(), ridePermitId));
        if (storedUnlockCode == null || !storedUnlockCode.equals(unlockCode))
            throw new NotFoundException("Unlock code not found or invalid");
    }


}
