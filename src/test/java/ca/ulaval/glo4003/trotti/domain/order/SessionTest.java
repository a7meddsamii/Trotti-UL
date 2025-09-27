package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTest {
    private static final String VALID_SESSION_A = "A2025";
    private static final String VALID_SESSION_H = "H2026";
    private static final String VALID_SESSION_E = "E2026";
    private static final String INVALID_SESSION = "B2027";

    @Test
    void givenValidSessionA_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> Session.from(VALID_SESSION_A);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSessionH_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> Session.from(VALID_SESSION_H);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenValidSessionE_whenCreate_thenObjectIsCreated() {
        Executable creation = () -> Session.from(VALID_SESSION_E);

        Assertions.assertDoesNotThrow(creation);
    }

    @Test
    void givenNullSession_whenCreate_thenThrowsException() {
        Executable creation = () -> Session.from(null);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptySession_whenCreate_thenThrowsException() {
        Executable creation = () -> Session.from(StringUtils.EMPTY);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenInvalidSession_whenCreate_thenThrowsException() {
        Executable creation = () -> Session.from(INVALID_SESSION);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoSessionsWithSameValue_whenCompare_thenTheyAreEqual() {
        Session session1 = Session.from(VALID_SESSION_A);
        Session session2 = Session.from(VALID_SESSION_A);

        Assertions.assertEquals(session1, session2);
        Assertions.assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void givenTwoSessionsWithDifferentValues_whenCompare_thenTheyAreNotEqual() {
        Session session1 = Session.from(VALID_SESSION_A);
        Session session2 = Session.from(VALID_SESSION_H);

        Assertions.assertNotEquals(session1, session2);
    }

    @Test
    void givenValidSession_whenToString_thenReturnSameString() {
        Session session = Session.from(VALID_SESSION_E);

        Assertions.assertEquals(VALID_SESSION_E, session.toString());
    }
}
