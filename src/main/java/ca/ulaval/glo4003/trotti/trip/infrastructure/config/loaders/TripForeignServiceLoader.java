package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.store.GuavaUnlockCodeStore;

public class TripForeignServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadGuavaCachingStore();
    }

    private void loadGuavaCachingStore() {
        UnlockCodeStore unlockCodeStore = new GuavaUnlockCodeStore();
        this.resourceLocator.register(UnlockCodeStore.class, unlockCodeStore);
    }
}
