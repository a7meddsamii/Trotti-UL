package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnlockCodeTest {

    private static final String NUMERIC_REGEX = "\\d+";
    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static final Idul ANOTHER_TRAVELER_ID = Idul.from("anotherTravelerId");

    private UnlockCode unlockCode;

    @BeforeEach
    void setup() {
        unlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID);
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
        UnlockCode firstUnlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID);
        UnlockCode secondUnlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID);

        Assertions.assertNotEquals(firstUnlockCode.getCode(), secondUnlockCode.getCode());
    }

    @Test
    void givenSameCodeValue_whenBelongsToTravelerAndIsValid_thenReturnsTrue() {
        Assertions.assertTrue(unlockCode.belongsToTravelerAndIsValid(unlockCode, A_TRAVELER_ID));
    }

    @Test
    void givenDifferentCodeValue_whenBelongsToTravelerAndIsValid_thenReturnsFalse() {
        UnlockCode differentUnlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID);

        Assertions.assertFalse(
                unlockCode.belongsToTravelerAndIsValid(differentUnlockCode, A_TRAVELER_ID));
    }

    @Test
    void givenDifferentTravelerId_whenBelongsToTravelerAndIsValid_thenReturnsFalse() {
        Assertions.assertFalse(
                unlockCode.belongsToTravelerAndIsValid(unlockCode, ANOTHER_TRAVELER_ID));
    }

    @Test
    void givenMatchingTravelerAndCodeAndNotExpired_whenBelongsToTravelerAndIsValid_thenReturnsTrue() {
        Assertions.assertTrue(unlockCode.belongsToTravelerAndIsValid(unlockCode, A_TRAVELER_ID));
    }
}
