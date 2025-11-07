package ca.ulaval.glo4003.trotti.order.domain.values;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTest {

    private static final LocalDate VALID_START_DATE = LocalDate.of(2025, Month.SEPTEMBER, 2);
    private static final LocalDate VALID_END_DATE = LocalDate.of(2025, Month.DECEMBER, 12);
    private static final LocalDate DATE_INSIDE_RANGE = LocalDate.of(2025, Month.SEPTEMBER, 2);
    private static final LocalDate DATE_OUTSIDE_RANGE = LocalDate.of(2025, Month.JANUARY, 12);

    @Test
    void givenValidFallSemesterAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidWinterSemesterAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(Semester.WINTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSummerSessionAndDates_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> new Session(Semester.SUMMER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenTwoSessionsWithSameValues_whenCompare_thenTheyAreEqual() {
        Session session1 = new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertEquals(session1, session2);
        Assertions.assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void givenTwoSessionsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        Session session1 = new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);
        Session session2 = new Session(Semester.WINTER, VALID_START_DATE, VALID_END_DATE);

        Assertions.assertNotEquals(session1, session2);
    }

    @Test
    void givenDateWithinSession_whenContains_thenTrue() {
        Session session = new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);

        boolean contains = session.contains(DATE_INSIDE_RANGE);

        Assertions.assertTrue(contains);
    }

    @Test
    void givenDateOutsideSession_whenContains_thenFalse() {
        Session session = new Session(Semester.FALL, VALID_START_DATE, VALID_END_DATE);

        boolean contains = session.contains(DATE_OUTSIDE_RANGE);

        Assertions.assertFalse(contains);
    }
}
