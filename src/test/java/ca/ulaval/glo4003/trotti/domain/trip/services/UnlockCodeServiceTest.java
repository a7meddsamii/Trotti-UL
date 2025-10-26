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
    private static UnlockCode storedUnlockCode;
    private static UnlockCode providedUnlockCode;
    private static final Clock NOW = Clock.systemUTC();

    private UnlockCodeStore unlockCodeStore;
    private UnlockCodeService unlockCodeService;

    @BeforeEach
    void setup() {
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        unlockCodeService = new UnlockCodeService(unlockCodeStore, NOW);
        storedUnlockCode = Mockito.mock(UnlockCode.class);
        providedUnlockCode = Mockito.mock(UnlockCode.class);
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
    void givenNoStoredUnlockCode_whenValidateAndRevoke_thenThrowsUnlockCodeException() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID)).thenReturn(Optional.empty());

        Executable action =
                () -> unlockCodeService.validateAndRevoke(providedUnlockCode, A_TRAVELER_ID);

        Assertions.assertThrows(UnlockCodeException.class, action);
    }

    @Test
    void givenStoredUnlockCodeThatIsInvalid_whenValidateAndRevoke_thenThrowsUnlockCodeException() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));
        Mockito.when(
                storedUnlockCode.belongsToTravelerAndIsValid(providedUnlockCode, A_TRAVELER_ID))
                .thenReturn(false);

        Executable action =
                () -> unlockCodeService.validateAndRevoke(providedUnlockCode, A_TRAVELER_ID);

        Assertions.assertThrows(UnlockCodeException.class, action);
        Mockito.verify(unlockCodeStore, Mockito.never()).revoke(A_TRAVELER_ID);
    }

    @Test
    void givenStoredUnlockCodeThatIsValid_whenValidateAndRevoke_thenRevokesUnlockCode() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));
        Mockito.when(
                storedUnlockCode.belongsToTravelerAndIsValid(providedUnlockCode, A_TRAVELER_ID))
                .thenReturn(true);

        unlockCodeService.validateAndRevoke(providedUnlockCode, A_TRAVELER_ID);

        Mockito.verify(unlockCodeStore).revoke(A_TRAVELER_ID);
    }

    @Test
    void givenStoredUnlockCodeThatIsValid_whenValidateAndRevoke_thenNoExceptionIsThrown() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));
        Mockito.when(
                storedUnlockCode.belongsToTravelerAndIsValid(providedUnlockCode, A_TRAVELER_ID))
                .thenReturn(true);

        Executable action =
                () -> unlockCodeService.validateAndRevoke(providedUnlockCode, A_TRAVELER_ID);

        Assertions.assertDoesNotThrow(action);
    }
}
