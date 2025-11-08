package ca.ulaval.glo4003.trotti.commons.domain;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.authentication.domain.values.HashedPassword;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class HashedPasswordTest {
    private static final String HASHED_PASSWORD = "hashed-password";
    private static final String DIFFERENT_HASHED_PASSWORD = "different-hashed-password";
    private static final String VALID_RAW_PASSWORD = "ValidPass123!";
    private static final String BLANK_PASSWORD = "   ";
    private static final String NULL_PASSWORD = null;

    private PasswordHasher hasher;

    @BeforeEach
    void setup() {
        hasher = Mockito.mock(PasswordHasher.class);
    }

    @Test
    void givenNullHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> HashedPassword.fromHashed(NULL_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptyHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> HashedPassword.fromHashed(StringUtils.EMPTY, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenBlankHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> HashedPassword.fromHashed(BLANK_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoPasswordsWithSameValue_whenCompare_thenTheyAreEqual() {
        HashedPassword hashedPassword1 = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);
        HashedPassword hashedPassword2 = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);

        Assertions.assertEquals(hashedPassword1, hashedPassword2);
        Assertions.assertEquals(hashedPassword1.hashCode(), hashedPassword2.hashCode());
    }

    @Test
    void givenTwoPasswordsWithDifferentValue_whenCompare_thenTheyAreNotEqual() {
        HashedPassword hashedPassword1 = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);
        HashedPassword hashedPassword2 = HashedPassword.fromHashed(DIFFERENT_HASHED_PASSWORD, hasher);

        Assertions.assertNotEquals(hashedPassword1, hashedPassword2);
    }
	
	@Test
	void givenRawPassword_whenMatches_thenPasswordHasherMatchesIsCalled() {
		HashedPassword hashedPassword = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);
		Mockito.when(hasher.matches(VALID_RAW_PASSWORD, HASHED_PASSWORD))
				.thenReturn(true);
		
		hashedPassword.matches(VALID_RAW_PASSWORD);
		
		Mockito.verify(hasher).matches(VALID_RAW_PASSWORD, HASHED_PASSWORD);
	}
	
    @Test
    void givenMatchingRawPassword_whenMatches_thenReturnTrue() {
        HashedPassword hashedPassword = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(VALID_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(true);

        boolean result = hashedPassword.matches(VALID_RAW_PASSWORD);

        Assertions.assertTrue(result);
    }

    @Test
    void givenNonMatchingRawPassword_whenMatches_thenReturnFalse() {
        HashedPassword hashedPassword = HashedPassword.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(VALID_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(false);

        boolean result = hashedPassword.matches(VALID_RAW_PASSWORD);

        Assertions.assertFalse(result);
    }
}
