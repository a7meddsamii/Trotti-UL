package ca.ulaval.glo4003.trotti.infrastructure.trip.store;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GuavaUnlockCodeStoreTest {

    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static final String CODE = "1234";

    private UnlockCode unlockCode;

    private UnlockCodeStore store;

    @BeforeEach
    void setup() {
        store = new GuavaUnlockCodeStore();
        unlockCode = Mockito.mock(UnlockCode.class);
    }

    @Test
    void whenStore_thenCodeIsRetrieved() {
        Mockito.when(unlockCode.getTravelerId()).thenReturn(A_TRAVELER_ID);
        Mockito.when(unlockCode.getCode()).thenReturn(CODE);

        store.store(unlockCode);

        Optional<UnlockCode> retrievedCode = store.getByTravelerId(A_TRAVELER_ID);
        Assertions.assertTrue(retrievedCode.isPresent());
        Assertions.assertEquals(CODE, retrievedCode.get().getCode());
    }

    @Test
    void whenRevoke_thenCodeIsNotRetrievable() {
        Mockito.when(unlockCode.getTravelerId()).thenReturn(A_TRAVELER_ID);
        store.store(unlockCode);

        store.revoke(A_TRAVELER_ID);

        Optional<UnlockCode> retrievedCode = store.getByTravelerId(A_TRAVELER_ID);
        Assertions.assertTrue(retrievedCode.isEmpty());
    }

    @Test
    void givenNoCodeStored_whenGetByTravelerId_thenReturnsEmptyOptional() {
        Optional<UnlockCode> retrievedCode = store.getByTravelerId(A_TRAVELER_ID);

        Assertions.assertTrue(retrievedCode.isEmpty());
    }
}
