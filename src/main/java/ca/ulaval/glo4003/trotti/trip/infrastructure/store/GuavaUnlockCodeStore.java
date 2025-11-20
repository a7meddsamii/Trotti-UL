package ca.ulaval.glo4003.trotti.trip.infrastructure.store;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Clock;
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
    public UnlockCode get(Idul idul, RidePermitId ridePermitId, Clock clock) {
        UnlockCode existingCode = codeCache.getIfPresent(UnlockCodeKey.from(idul, ridePermitId));
        if (existingCode != null) {
            return existingCode;
        }

        UnlockCode unlockCode = UnlockCode.generate(idul, ridePermitId, clock);
        codeCache.put(UnlockCodeKey.from(idul, ridePermitId), unlockCode);

        return unlockCode;
    }

    @Override
    public void revoke(Idul idul, RidePermitId ridePermitId) {
        codeCache.invalidate(UnlockCodeKey.from(idul, ridePermitId));
    }

    @Override
    public void validate(Idul idul, RidePermitId ridePermitId, String unlockCode) {
        UnlockCodeKey unlockCodeKey = UnlockCodeKey.from(idul, ridePermitId);
        UnlockCode storedUnlockCode = codeCache.getIfPresent(unlockCodeKey);

        if (storedUnlockCode == null || !storedUnlockCode.matches(idul, ridePermitId, unlockCode))
            throw new NotFoundException("Unlock code not found");
    }
}
