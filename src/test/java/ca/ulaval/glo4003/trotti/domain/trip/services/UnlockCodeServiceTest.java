package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.time.Clock;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeServiceTest {

    private static final RidePermitId AN_ID = RidePermitId.randomId();
    private static final Clock NOW = Clock.systemUTC();

    private UnlockCodeStore unlockCodeStore;
    private UnlockCodeService unlockCodeService;

    @BeforeEach
    void setup() {
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        unlockCodeService = new UnlockCodeService(unlockCodeStore, NOW);
    }

    @Test
    void givenExistingUnlockCode_whenRequestUnlockCode_thenReturnsExistingUnlockCode() {
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(unlockCodeStore.getByRidePermitId(AN_ID)).thenReturn(Optional.of(unlockCode));

        UnlockCode generatedUnlockCode = unlockCodeService.requestUnlockCode(AN_ID);

        Assertions.assertEquals(unlockCode, generatedUnlockCode);
        Mockito.verify(unlockCodeStore, Mockito.never()).store(Mockito.any(UnlockCode.class));
    }

    @Test
    void givenNoExistingUnlockCode_whenRequestUnlockCode_thenStoresNewUnlockCode() {
        Mockito.when(unlockCodeStore.getByRidePermitId(AN_ID)).thenReturn(Optional.empty());

        UnlockCode unlockCode = unlockCodeService.requestUnlockCode(AN_ID);

        Mockito.verify(unlockCodeStore).store(unlockCode);
    }
}
