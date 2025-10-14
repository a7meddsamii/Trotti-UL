package ca.ulaval.glo4003.trotti.infrastructure.trip.store;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GuavaUnlockCodeStoreTest {

    private static final Id RIDE_PERMIT_ID = Id.randomId();
    private static final String CODE = "1234";

    private UnlockCode unlockCode;

    private UnlockCodeStore store = new GuavaUnlockCodeStore();

    @BeforeEach
    void setup() {
        unlockCode = Mockito.mock(UnlockCode.class);
    }

    @Test
    void whenStore_thenCodeIsRetrieved() {
        Mockito.when(unlockCode.getRidePermitId()).thenReturn(RIDE_PERMIT_ID);
        Mockito.when(unlockCode.getCode()).thenReturn(CODE);

        store.store(unlockCode);

        Optional<UnlockCode> retrievedCode = store.getByRidePermitId(RIDE_PERMIT_ID);
        Assertions.assertTrue(retrievedCode.isPresent());
        Assertions.assertEquals(CODE, retrievedCode.get().getCode());
    }

    @Test
    void whenRevoke_thenCodeIsNotRetrievable() {
        Mockito.when(unlockCode.getRidePermitId()).thenReturn(RIDE_PERMIT_ID);
        store.store(unlockCode);

        store.revoke(RIDE_PERMIT_ID);

        Optional<UnlockCode> retrievedCode = store.getByRidePermitId(RIDE_PERMIT_ID);
        Assertions.assertTrue(retrievedCode.isEmpty());
    }

    @Test
    void givenNoCodeStored_whenGetByRidePermitId_thenReturnsEmptyOptional() {
        Optional<UnlockCode> retrievedCode = store.getByRidePermitId(RIDE_PERMIT_ID);

        Assertions.assertTrue(retrievedCode.isEmpty());
    }
}
