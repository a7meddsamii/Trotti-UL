package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

class UnlockCodeTest {

    private static final Instant AN_INSTANT = Instant.parse("2025-10-13T10:00:00Z");
    private static final String NUMERIC_REGEX = "\\d+";
    private static final Id AN_ID = Id.randomId();
    private UnlockCode unlockCode;

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs4DigistsOrMore() {
        Clock now = Clock.fixed(AN_INSTANT, ZoneOffset.UTC);

        unlockCode = UnlockCode.generateFromRidePermit(Id.randomId(), now);

        Assertions.assertNotNull(unlockCode);
        Assertions.assertTrue(unlockCode.getCode().length() >= 4);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeLengthIs6DigitsOrLess() {
        Clock now = Clock.fixed(AN_INSTANT, ZoneOffset.UTC);

        unlockCode = UnlockCode.generateFromRidePermit(AN_ID, now);

        Assertions.assertTrue(unlockCode.getCode().length() <= 6);
    }

    @Test
    void whenCreatingUnlockCode_thenCodeIsNumeric() {
        Clock now = Clock.fixed(AN_INSTANT, ZoneOffset.UTC);

        unlockCode = UnlockCode.generateFromRidePermit(AN_ID, now);

        Assertions.assertTrue(unlockCode.getCode().matches(NUMERIC_REGEX));
    }

    @Test
    void whenCreatingTwoUnlockCodes_thenTheyAreDifferent() {
        Clock now = Clock.fixed(AN_INSTANT, ZoneOffset.UTC);

        UnlockCode firstUnlockCode = UnlockCode.generateFromRidePermit(AN_ID, now);
        UnlockCode secondUnlockCode = UnlockCode.generateFromRidePermit(AN_ID, now);

        Assertions.assertNotEquals(firstUnlockCode.getCode(), secondUnlockCode.getCode());
    }

    @Test
    void whenCreatingUnlockCode_thenItIsNotExpired() {
        Clock now = Clock.fixed(AN_INSTANT, ZoneOffset.UTC);

        unlockCode = UnlockCode.generateFromRidePermit(AN_ID, now);

        Assertions.assertFalse(unlockCode.isExpired());
    }
}