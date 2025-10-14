package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class UnlockCodeServiceTest {

    private static final Id AN_ID = Id.randomId();

    private UnlockCode unlockCode;
    private UnlockCodeStore unlockCodeStore;
    private UnlockCodeService unlockCodeService;

    @BeforeEach
    void setup() {
        unlockCode = Mockito.mock(UnlockCode.class);
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        unlockCodeService = new UnlockCodeService(unlockCodeStore);
    }

    @Test
    void givenExistingUnlockCode_whenRequestUnlockCode_thenReturnsExistingUnlockCode() {
        Mockito.when(unlockCodeStore.getByRidePermitId(AN_ID)).thenReturn(Optional.of(unlockCode));

        UnlockCode result = unlockCodeService.requestUnlockCode(AN_ID);

        Assertions.assertEquals(unlockCode, result);
        Mockito.verify(unlockCodeStore, Mockito.never()).store(Mockito.any(UnlockCode.class));
    }

    @Test
    void givenNoExistingUnlockCode_whenRequestUnlockCode_thenGeneratesAndStoresNewUnlockCode() {
        Mockito.when(unlockCodeStore.getByRidePermitId(AN_ID)).thenReturn(Optional.empty());
        Mockito.mockStatic(UnlockCode.class).when(() -> UnlockCode.generateFromRidePermit(AN_ID)).thenReturn(unlockCode);

        UnlockCode result = unlockCodeService.requestUnlockCode(AN_ID);

        Assertions.assertEquals(unlockCode, result);
        Mockito.verify(unlockCodeStore).store(unlockCode);
    }

}