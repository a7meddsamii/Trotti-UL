package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.UnlockCodeException;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import java.time.Clock;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class UnlockCodeServiceTest {

    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static UnlockCode VALID_CODE_VALUE;
    private static UnlockCode INVALID_CODE_VALUE;
    private static final Clock NOW = Clock.systemUTC();

    private UnlockCodeStore unlockCodeStore;
    private UnlockCodeService unlockCodeService;

    @BeforeEach
    void setup() {
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        unlockCodeService = new UnlockCodeService(unlockCodeStore, NOW);
        VALID_CODE_VALUE = Mockito.mock(UnlockCode.class);
        INVALID_CODE_VALUE = Mockito.mock(UnlockCode.class);
    }

    @Test
    void givenExistingUnlockCode_whenRequestUnlockCode_thenReturnsExistingUnlockCode() {
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(unlockCode));

        UnlockCode generatedUnlockCode = unlockCodeService.requestUnlockCode(A_TRAVELER_ID);

        Assertions.assertEquals(unlockCode, generatedUnlockCode);
        Mockito.verify(unlockCodeStore, Mockito.never()).store(Mockito.any(UnlockCode.class));
    }

    @Test
    void givenNoExistingUnlockCode_whenRequestUnlockCode_thenStoresNewUnlockCode() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID)).thenReturn(Optional.empty());

        UnlockCode unlockCode = unlockCodeService.requestUnlockCode(A_TRAVELER_ID);

        Mockito.verify(unlockCodeStore).store(unlockCode);
    }

    @Test
    void givenInvalidCodeForTraveler_whenValidateCode_thenThrowsUnlockCodeException() {
        Mockito.when(unlockCodeStore.isValid(INVALID_CODE_VALUE, A_TRAVELER_ID)).thenReturn(false);

        Executable validation =
                () -> unlockCodeService.validateCode(INVALID_CODE_VALUE, A_TRAVELER_ID);

        Assertions.assertThrows(UnlockCodeException.class, validation);
    }

    @Test
    void givenValidCodeAndTravelerId_whenValidateCode_thenCallsUnlockCodeStoreWithCorrectParameters() {
        Mockito.when(unlockCodeStore.isValid(VALID_CODE_VALUE, A_TRAVELER_ID)).thenReturn(true);

        unlockCodeService.validateCode(VALID_CODE_VALUE, A_TRAVELER_ID);

        Mockito.verify(unlockCodeStore).isValid(VALID_CODE_VALUE, A_TRAVELER_ID);
    }

    @Test
    void givenValidCodeAndTravelerId_whenValidateCode_thenNoExceptionIsThrown() {
        Mockito.when(unlockCodeStore.isValid(VALID_CODE_VALUE, A_TRAVELER_ID)).thenReturn(true);

        Executable validation =
                () -> unlockCodeService.validateCode(VALID_CODE_VALUE, A_TRAVELER_ID);

        Assertions.assertDoesNotThrow(validation);
    }
}
