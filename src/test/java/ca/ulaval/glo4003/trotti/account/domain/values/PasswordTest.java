package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class PasswordTest {
    private static final String HASHED_PASSWORD = "hashed-password";
    private static final String DIFFERENT_HASHED_PASSWORD = "different-hashed-password";
    private static final String VALID_RAW_PASSWORD = "ValidPass123!";
    private static final String BLANK_PASSWORD = "   ";
    private static final String NULL_PASSWORD = null;

    private static final String WEAK_PASSWORD_NO_UPPERCASE = "lowercase1!";
    private static final String WEAK_PASSWORD_NO_DIGIT = "NoDigit!";
    private static final String WEAK_PASSWORD_NO_SPECIAL = "NoSpecial1";
    private static final String WEAK_PASSWORD_TOO_SHORT = "Short1!";

    private PasswordHasher hasher;

    @BeforeEach
    void setup() {
        hasher = Mockito.mock(PasswordHasher.class);
    }

    @Test
    void givenValidRawPassword_whenCreatingNewPassword_thenPasswordIsCreated() {
        Mockito.when(hasher.hash(VALID_RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);

        Password password = Password.fromPlain(VALID_RAW_PASSWORD, hasher);

        Assertions.assertNotNull(password);
    }

    @Test
    void givenValidRawPassword_whenCreatingNewPassword_thenPasswordHasherIsCalled() {
        Mockito.when(hasher.hash(VALID_RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);

        Password.fromPlain(VALID_RAW_PASSWORD, hasher);

        Mockito.verify(hasher).hash(VALID_RAW_PASSWORD);
    }

    @Test
    void givenNullRawPassword_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(NULL_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptyRawPassword_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(StringUtils.EMPTY, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenBlankRawPassword_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(BLANK_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenWeakPasswordWithoutUppercase_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(WEAK_PASSWORD_NO_UPPERCASE, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenWeakPasswordWithoutDigit_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(WEAK_PASSWORD_NO_DIGIT, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenWeakPasswordWithoutSpecialChar_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(WEAK_PASSWORD_NO_SPECIAL, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenWeakPasswordTooShort_whenCreatingNewPassword_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromPlain(WEAK_PASSWORD_TOO_SHORT, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenValidHashedPassword_whenCreatingFromHash_thenPasswordIsCreated() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);

        Assertions.assertNotNull(password);
    }

    @Test
    void givenNullHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromHashed(NULL_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenEmptyHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromHashed(StringUtils.EMPTY, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenBlankHashedPassword_whenCreatingFromHash_thenThrowInvalidParameterException() {
        Executable creation = () -> Password.fromHashed(BLANK_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, creation);
    }

    @Test
    void givenTwoPasswordsWithSameValue_whenCompare_thenTheyAreEqual() {
        Password password1 = Password.fromHashed(HASHED_PASSWORD, hasher);
        Password password2 = Password.fromHashed(HASHED_PASSWORD, hasher);

        Assertions.assertEquals(password1, password2);
        Assertions.assertEquals(password1.hashCode(), password2.hashCode());
    }

    @Test
    void givenTwoPasswordsWithDifferentValue_whenCompare_thenTheyAreNotEqual() {
        Password password1 = Password.fromHashed(HASHED_PASSWORD, hasher);
        Password password2 = Password.fromHashed(DIFFERENT_HASHED_PASSWORD, hasher);

        Assertions.assertNotEquals(password1, password2);
    }

    @Test
    void givenMatchingRawPassword_whenMatches_thenReturnTrue() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(true);

        boolean result = password.matches(AccountFixture.A_RAW_PASSWORD);

        Assertions.assertTrue(result);
    }

    @Test
    void givenNonMatchingRawPassword_whenMatches_thenReturnFalse() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(false);

        boolean result = password.matches(AccountFixture.A_RAW_PASSWORD);

        Assertions.assertFalse(result);
    }

    @Test
    void givenRawPassword_whenMatches_thenPasswordHasherMatchesIsCalled() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(true);

        password.matches(AccountFixture.A_RAW_PASSWORD);

        Mockito.verify(hasher).matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD);
    }
}
