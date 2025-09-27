package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.LocalDate;
import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTest {
    public static final String VALID_SESSION_A = "A25";
    private static final String VALID_SESSION_H = "H26";
    private static final String VALID_SESSION_E = "E26";
    private static final String INVALID_SESSION = "B27";
    public static final LocalDate VALID_START_DATE = LocalDate.of(2025, Calendar.SEPTEMBER, 2);
    public static final LocalDate VALID_END_DATE = LocalDate.of(2025, Calendar.DECEMBER, 12);
    private static final LocalDate INVALID_END_DATE = LocalDate.of(2025, Calendar.AUGUST, 12);

    @Test
    void givenValidSessionAAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSessionHAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(VALID_SESSION_H, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSessionEAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(VALID_SESSION_E, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenNullSession_whenCreate_thenThrowsException() {
        Executable creation = () -> new Session(null, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptySession_whenCreate_thenThrowsException() {
        Executable creation =
                () -> new Session(StringUtils.EMPTY, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenInvalidSession_whenCreate_thenThrowsException() {
        Executable creation = () -> new Session(INVALID_SESSION, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNullStartDate_whenCreate_thenThrowsException() {
        Executable creation = () -> new Session(VALID_SESSION_A, null, VALID_END_DATE);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenNullEndDate_whenCreate_thenThrowsException() {
        Executable creation = () -> new Session(VALID_SESSION_A, VALID_START_DATE, null);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenInvalidDates_whenCreate_thenThrowsException() {
        Executable creation =
                () -> new Session(VALID_SESSION_A, VALID_START_DATE, INVALID_END_DATE);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoSessionsWithSameValues_whenCompare_thenTheyAreEqual() {
        Session session1 = new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertEquals(session1, session2);
        Assertions.assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void givenTwoSessionsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        Session session1 = new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(VALID_SESSION_H, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertNotEquals(session1, session2);
    }

    @Test
    void givenDateWithinSession_whenIncludes_thenIncluded() {
        Session session = new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);
        LocalDate dateInside = LocalDate.of(2025, 10, 10);

        Assertions.assertTrue(session.includes(dateInside));
    }

    @Test
    void givenDateOutsideSession_whenIncludes_thenNotIncluded() {
        Session session = new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);
        LocalDate dateOutside = LocalDate.of(2025, 1, 1);

        Assertions.assertFalse(session.includes(dateOutside));
    }
}
