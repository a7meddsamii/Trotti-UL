package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidIdulException;
import org.junit.jupiter.api.Test;

class IdulTest {
    private static final String VALID_IDUL = "CM1B2G45";
    private static final String DIFFERENT_IDUL = "XYZ12345";
    private static final String EMPTY_IDUL = "";
    private static final String NULL_IDUL = null;

    @Test
    void givenValidIdul_whenCreate_thenSucceeds() {
        Idul idul = Idul.from(VALID_IDUL);

        assertEquals(VALID_IDUL, idul.getValue());
        assertEquals(VALID_IDUL, idul.toString());
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowInvalidIdulException() {
        assertThrows(InvalidIdulException.class, () -> Idul.from(NULL_IDUL));
    }

    @Test
    void givenEmptyIdul_whenCreate_thenThrowInvalidIdulException() {
        assertThrows(InvalidIdulException.class, () -> Idul.from(EMPTY_IDUL));
    }

    @Test
    void givenTwoIdulWithSameValue_whenCompare_thenTheyAreEqual() {
        Idul idul1 = Idul.from(VALID_IDUL);
        Idul idul2 = Idul.from(VALID_IDUL);

        assertEquals(idul1, idul2);
        assertEquals(idul1.hashCode(), idul2.hashCode());
    }

    @Test
    void givenDifferentIduls_whenCompare_thenTheyAreNotEqual() {
        Idul idul1 = Idul.from(VALID_IDUL);
        Idul idul2 = Idul.from(DIFFERENT_IDUL);

        assertNotEquals(idul1, idul2);
    }
}
