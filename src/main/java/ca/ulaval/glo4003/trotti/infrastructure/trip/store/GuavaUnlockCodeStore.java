package ca.ulaval.glo4003.trotti.infrastructure.trip.store;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GuavaUnlockCodeStore implements UnlockCodeStore {

    private static final long DEFAULT_MAX_SIZE = 1000;
    private static final long DEFAULT_EXPIRATION_SECONDS = 60;

    private final Cache<Idul, UnlockCode> codeCache;

    public GuavaUnlockCodeStore() {
        this.codeCache = CacheBuilder.newBuilder().maximumSize(DEFAULT_MAX_SIZE)
                .expireAfterWrite(DEFAULT_EXPIRATION_SECONDS, TimeUnit.SECONDS).recordStats()
                .build();
    }

    @Override
    public void store(UnlockCode unlockCode) {
        codeCache.put(unlockCode.getTravelerId(), unlockCode);
    }

    @Override
    public void revoke(Idul travelerId) {
        codeCache.invalidate(travelerId);
    }

    @Override
    public Optional<UnlockCode> getByTravelerId(Idul travelerId) {
        UnlockCode unlockCode = codeCache.getIfPresent(travelerId);
        return Optional.ofNullable(unlockCode);
    }

    public boolean isValid(String codeValue, Idul travelerId) {
        Optional<UnlockCode> code = Optional.ofNullable(codeCache.getIfPresent(travelerId));

        return code.isPresent() && code.get().isCorrectValue(codeValue);
    }

}
