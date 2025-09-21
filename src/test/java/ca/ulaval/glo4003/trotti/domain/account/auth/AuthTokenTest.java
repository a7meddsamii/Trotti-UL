package ca.ulaval.glo4003.trotti.domain.account.auth;


import ca.ulaval.glo4003.trotti.domain.account.exception.MalformedTokenException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AuthTokenTest {
	private final String TOKEN_VALUE = "token";
	private final String DIFFERENT_TOKEN_VALUE = "differentToken";
	private final Object OBJECT_OF_DIFFERENT_TYPE = new Object();
	
	@Test
	void givenBlankValue_whenCreatingAuthToken_thenThrowException() {
		Executable authTokenCreation = () -> AuthToken.from(StringUtils.EMPTY);
		
		Assertions.assertThrows(MalformedTokenException.class, authTokenCreation);
	}
	
	@Test
	void givenTwoTokenOfSameValue_whenComparing_thenTheyAreEqual() {
		AuthToken authToken = AuthToken.from(TOKEN_VALUE);
		AuthToken anotherAuthToken = AuthToken.from(TOKEN_VALUE);
		
		Assertions.assertEquals(authToken, anotherAuthToken);
	}
	
	@Test
	void givenTwoTokenOfDifferentValue_whenComparing_thenTheyAreNotEqual() {
		AuthToken authToken = AuthToken.from(TOKEN_VALUE);
		AuthToken anotherAuthToken = AuthToken.from(DIFFERENT_TOKEN_VALUE);
		
		Assertions.assertNotEquals(authToken, anotherAuthToken);
	}
	
	@Test
	void givenToken_whenComparingToDifferentType_thenTheyAreNotEqual() {
		AuthToken authToken = AuthToken.from(TOKEN_VALUE);
		
		Assertions.assertNotEquals(OBJECT_OF_DIFFERENT_TYPE, authToken);
	}
}