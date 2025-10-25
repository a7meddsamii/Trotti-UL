package ca.ulaval.glo4003.trotti.infrastructure.trip.store;

import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GuavaUnlockCodeStore implements UnlockCodeStore {

    private static final long DEFAULT_MAX_SIZE = 1000;
    private static final long DEFAULT_EXPIRATION_SECONDS = 60;

    private final Cache<RidePermitId, UnlockCode> codeCache;

    public GuavaUnlockCodeStore() {
        this.codeCache = CacheBuilder.newBuilder().maximumSize(DEFAULT_MAX_SIZE)
                .expireAfterWrite(DEFAULT_EXPIRATION_SECONDS, TimeUnit.SECONDS).recordStats()
                .build();
    }

    @Override
    public void store(UnlockCode unlockCode) {
        codeCache.put(unlockCode.getRidePermitId(), unlockCode);
    }

    @Override
    public void revoke(RidePermitId ridePermitId) {
        codeCache.invalidate(ridePermitId);
    }

    @Override
    public Optional<UnlockCode> getByRidePermitId(RidePermitId ridePermitId) {
        UnlockCode unlockCode = codeCache.getIfPresent(ridePermitId);
        return Optional.ofNullable(unlockCode);
    }
}
