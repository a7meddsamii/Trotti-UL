package ca.ulaval.glo4003.trotti.trip.infrastructure.store;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import java.time.Clock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class GuavaUnlockCodeStoreTest {

    private static final Idul IDUL = Idul.from("ABCD");
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final String CODE = "1234";

    private UnlockCodeStore store;
    private Clock fixedClock;

    @BeforeEach
    void setup() {
        store = new GuavaUnlockCodeStore();
        fixedClock = Clock.systemDefaultZone();
    }

    @Test
    void givenNoExistingCode_whenGet_thenGenerates() {
        UnlockCode generated = store.get(IDUL, RIDE_PERMIT_ID, fixedClock);

        Assertions.assertNotNull(generated);
        UnlockCode fetchedAgain = store.get(IDUL, RIDE_PERMIT_ID, fixedClock);
        Assertions.assertEquals(generated, fetchedAgain);
    }

    @Test
    void givenExistingCodeInCache_whenGet_thenReturnCurrentCode() {
        UnlockCode generatedUnlockCode = store.get(IDUL, RIDE_PERMIT_ID, fixedClock);

        UnlockCode result = store.get(IDUL, RIDE_PERMIT_ID, fixedClock);

        Assertions.assertEquals(generatedUnlockCode.getCode(), result.getCode());
    }

    @Test
    void givenMatchingStoredCode_whenValidate_thenNoExceptionThrown() {
        UnlockCode unlockCode = store.get(IDUL, RIDE_PERMIT_ID, fixedClock);

        Executable validateAction = () -> store.validate(unlockCode.getTravelerId(),
                unlockCode.getRidePermitId(), unlockCode.getCode());

        Assertions.assertDoesNotThrow(validateAction);
    }

    @Test
    void givenNoStoredCode_whenValidate_thenThrowsNotFoundException() {
        Executable validateAction = () -> store.validate(IDUL, RIDE_PERMIT_ID, CODE);

        Assertions.assertThrows(NotFoundException.class, validateAction);
    }

    @Test
    void givenNonMatchingStoredCode_whenValidate_thenThrowsNotFoundException() {
        store.get(IDUL, RIDE_PERMIT_ID, fixedClock);

        Executable validateAction = () -> store.validate(IDUL, RIDE_PERMIT_ID, "wrongCode");

        Assertions.assertThrows(NotFoundException.class, validateAction);
    }

    @Test
    void givenExistingCode_whenRevoke_thenSubsequentValidateThrowsNotFoundException() {
        store.get(IDUL, RIDE_PERMIT_ID, fixedClock);
        store.revoke(IDUL, RIDE_PERMIT_ID);

        Executable validateAction = () -> store.validate(IDUL, RIDE_PERMIT_ID, CODE);

        Assertions.assertThrows(NotFoundException.class, validateAction);
    }
}
