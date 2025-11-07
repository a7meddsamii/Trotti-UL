package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessionTokenTest {
    private static final String TOKEN_VALUE = "TokenValue";
    private static final String ANOTHER_TOKEN_VALUE = "AnotherTokenValue";
    private static final String BLANK_TOKEN_VALUE = "";

    @Test
    void givenBlankTokenValue_whenFrom_thenThrowsAuthenticationException() {
        Executable tokenCreationAttempt = () -> SessionToken.from(BLANK_TOKEN_VALUE);

        Assertions.assertThrows(AuthenticationException.class, tokenCreationAttempt);
    }

    @Test
    void givenValidTokenValue_whenFrom_thenCreatesSessionToken() {
        SessionToken sessionToken = SessionToken.from(TOKEN_VALUE);

        Assertions.assertEquals(TOKEN_VALUE, sessionToken.toString());
    }

    @Test
    void givenTwoSessionTokensWithSameValue_whenEquals_thenTheyAreEqual() {
        SessionToken sessionToken1 = SessionToken.from(TOKEN_VALUE);
        SessionToken sessionToken2 = SessionToken.from(TOKEN_VALUE);

        Assertions.assertEquals(sessionToken1, sessionToken2);
    }

    @Test
    void givenTwoSessionTokensWithDifferentValues_whenEquals_thenTheyAreNotEqual() {
        SessionToken sessionToken1 = SessionToken.from(TOKEN_VALUE);
        SessionToken sessionToken2 = SessionToken.from(ANOTHER_TOKEN_VALUE);

        Assertions.assertNotEquals(sessionToken1, sessionToken2);
    }
}
