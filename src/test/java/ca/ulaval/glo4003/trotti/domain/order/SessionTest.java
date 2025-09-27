package ca.ulaval.glo4003.trotti.domain.order;

import java.time.LocalDate;
import java.util.Calendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTest {
    public static final String VALID_SESSION_A = "A25";
    private static final String VALID_SESSION_H = "H26";
    private static final String VALID_SESSION_E = "E26";
    public static final LocalDate VALID_START_DATE = LocalDate.of(2025, Calendar.SEPTEMBER, 2);
    public static final LocalDate VALID_END_DATE = LocalDate.of(2025, Calendar.DECEMBER, 12);

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
