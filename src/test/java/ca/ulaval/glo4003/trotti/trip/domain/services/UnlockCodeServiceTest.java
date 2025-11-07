package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.UnlockCodeException;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
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

    private UnlockCodeStore unlockCodeStore;
    private UnlockCodeService unlockCodeService;

    @BeforeEach
    void setup() {
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        unlockCodeService = new UnlockCodeService(unlockCodeStore);
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
    void givenNoStoredUnlockCode_whenRevoke_thenThrowsUnlockCodeException() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID)).thenReturn(Optional.empty());

        Executable action = () -> unlockCodeService.revoke(providedUnlockCode);

        Assertions.assertThrows(UnlockCodeException.class, action);
    }

    @Test
    void givenStoredUnlockCodeThatIsInvalid_whenRevoke_thenThrowsUnlockCodeException() {
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));
        Mockito.when(
                storedUnlockCode.belongsToTravelerAndIsValid(providedUnlockCode, A_TRAVELER_ID))
                .thenReturn(false);

        Executable action = () -> unlockCodeService.revoke(providedUnlockCode);

        Assertions.assertThrows(UnlockCodeException.class, action);
        Mockito.verify(unlockCodeStore, Mockito.never()).revoke(A_TRAVELER_ID);
    }

    @Test
    void givenStoredUnlockCodeThatMatches_whenRevoke_thenRevokesUnlockCode() {
        UnlockCode storedUnlockCode = createUnlockCodeForTraveler(A_TRAVELER_ID);
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));

        unlockCodeService.revoke(storedUnlockCode);

        Mockito.verify(unlockCodeStore).revoke(A_TRAVELER_ID);
    }

    @Test
    void givenStoredUnlockCodeThatMatches_whenRevoke_thenNoExceptionIsThrown() {
        UnlockCode storedUnlockCode = createUnlockCodeForTraveler(A_TRAVELER_ID);
        Mockito.when(unlockCodeStore.getByTravelerId(A_TRAVELER_ID))
                .thenReturn(Optional.of(storedUnlockCode));

        Executable action = () -> unlockCodeService.revoke(storedUnlockCode);

        Assertions.assertDoesNotThrow(action);
    }

    private UnlockCode createUnlockCodeForTraveler(Idul travelerId) {
        UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(unlockCode.getTravelerId()).thenReturn(travelerId);
        return unlockCode;
    }
}
