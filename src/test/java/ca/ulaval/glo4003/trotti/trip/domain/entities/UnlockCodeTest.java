package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.time.Clock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnlockCodeTest {

    private static final String NUMERIC_REGEX = "\\d+";
    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static final RidePermitId A_RIDE_PERMIT_ID = RidePermitId.randomId();

    private UnlockCode unlockCode;
    private Clock clock = Clock.systemDefaultZone();

    @BeforeEach
    void setup() {
        unlockCode = UnlockCode.generate(A_TRAVELER_ID, A_RIDE_PERMIT_ID, clock);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs4DigistsOrMore() {
        Assertions.assertNotNull(unlockCode);
        Assertions.assertTrue(unlockCode.getCode().length() >= 4);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs6DigitsOrLess() {
        Assertions.assertTrue(unlockCode.getCode().length() <= 6);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeIsNumeric() {
        Assertions.assertTrue(unlockCode.getCode().matches(NUMERIC_REGEX));
    }

    @Test
    void whenCreatingTwoUnlockCodes_thenTheyAreDifferent() {
        UnlockCode firstUnlockCode = UnlockCode.generate(A_TRAVELER_ID, A_RIDE_PERMIT_ID, clock);
        UnlockCode secondUnlockCode = UnlockCode.generate(A_TRAVELER_ID, A_RIDE_PERMIT_ID, clock);

        Assertions.assertNotEquals(firstUnlockCode.getCode(), secondUnlockCode.getCode());
    }

    @Test
    void givenSameCodeValueSameIdulAndRidePermitId_whenMatches_thenReturnsTrue() {
        Assertions.assertTrue(
                unlockCode.matches(A_TRAVELER_ID, A_RIDE_PERMIT_ID, unlockCode.getCode()));
    }

    @Test
    void givenDifferentCodeValue_whenMatches_thenReturnsFalse() {
        UnlockCode differentUnlockCode =
                UnlockCode.generate(A_TRAVELER_ID, A_RIDE_PERMIT_ID, clock);

        Assertions.assertFalse(
                unlockCode.matches(A_TRAVELER_ID, A_RIDE_PERMIT_ID, differentUnlockCode.getCode()));
    }

}
