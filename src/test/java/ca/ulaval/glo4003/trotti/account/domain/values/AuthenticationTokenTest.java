package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.exceptions.MalformedTokenException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AuthenticationTokenTest {
    private static final String TOKEN_VALUE = "token";
    private static final String DIFFERENT_TOKEN_VALUE = "differentToken";
    private static final Object OBJECT_OF_DIFFERENT_TYPE = new Object();

    @Test
    void givenBlankValue_whenCreatingAuthToken_thenThrowException() {
        Executable authTokenCreation = () -> AuthenticationToken.from(StringUtils.EMPTY);

        Assertions.assertThrows(MalformedTokenException.class, authTokenCreation);
    }

    @Test
    void givenTwoTokenOfSameValue_whenComparing_thenTheyAreEqual() {
        AuthenticationToken authenticationToken = AuthenticationToken.from(TOKEN_VALUE);
        AuthenticationToken anotherAuthenticationToken = AuthenticationToken.from(TOKEN_VALUE);

        Assertions.assertEquals(authenticationToken, anotherAuthenticationToken);
    }

    @Test
    void givenTwoTokenOfDifferentValue_whenComparing_thenTheyAreNotEqual() {
        AuthenticationToken authenticationToken = AuthenticationToken.from(TOKEN_VALUE);
        AuthenticationToken anotherAuthenticationToken =
                AuthenticationToken.from(DIFFERENT_TOKEN_VALUE);

        Assertions.assertNotEquals(authenticationToken, anotherAuthenticationToken);
    }

    @Test
    void givenToken_whenComparingToDifferentType_thenTheyAreNotEqual() {
        AuthenticationToken authenticationToken = AuthenticationToken.from(TOKEN_VALUE);

        Assertions.assertNotEquals(OBJECT_OF_DIFFERENT_TYPE, authenticationToken);
    }
}
