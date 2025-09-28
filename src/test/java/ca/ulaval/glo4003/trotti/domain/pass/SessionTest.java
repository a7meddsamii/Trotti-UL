package ca.ulaval.glo4003.trotti.domain.pass;

import java.time.LocalDate;
import java.util.Calendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTest {
    public static final Semester FALL_SEMESTER = Semester.FALL;
    public static final Semester WINTER_SEMESTER = Semester.WINTER;
    public static final Semester SUMMER_SEMESTER = Semester.SUMMER;
    public static final LocalDate VALID_START_DATE = LocalDate.of(2025, Calendar.SEPTEMBER, 2);
    public static final LocalDate VALID_END_DATE = LocalDate.of(2025, Calendar.DECEMBER, 12);

    @Test
    void givenValidFallSemesterAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidWinterSemesterAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(WINTER_SEMESTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSummerSessionAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(SUMMER_SEMESTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenTwoSessionsWithSameValues_whenCompare_thenTheyAreEqual() {
        Session session1 = new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertEquals(session1, session2);
        Assertions.assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void givenTwoSessionsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        Session session1 = new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(WINTER_SEMESTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertNotEquals(session1, session2);
    }

    @Test
    void givenDateWithinSession_whenContains_thenTrue() {
        Session session = new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);
        LocalDate dateInside = LocalDate.of(2025, 10, 10);

        boolean contains = session.contains(dateInside);

        Assertions.assertTrue(contains);
    }

    @Test
    void givenDateOutsideSession_whenContains_thenFalse() {
        Session session = new Session(FALL_SEMESTER, VALID_START_DATE, VALID_END_DATE);
        LocalDate dateOutside = LocalDate.of(2025, 1, 1);

        boolean contains = session.contains(dateOutside);

        Assertions.assertFalse(contains);
    }
}
