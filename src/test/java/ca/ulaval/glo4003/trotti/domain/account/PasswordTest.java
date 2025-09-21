package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PasswordTest {
    private static final String VALID_PASSWORD = "StrongPass1!";
    private static final String TOO_SHORT_PASSWORD = "Ab1!";
    private static final String MISSING_UPPERCASE = "strongpass1!";
    private static final String MISSING_SPECIAL_CHAR = "StrongPass1";
    private static final String MISSING_NUMBER = "StrongPass!";
    private static final String NULL_PASSWORD = null;
    private static final String HASHED_PASSWORD = "hashed-password";

    private PasswordHasher hasher;

    @BeforeEach
    void setup() {
        hasher = Mockito.mock(PasswordHasher.class);
    }

    @Test
    void givenValidPassword_whenCreatePassword_thenHasherIsCalled() {
        new Password(VALID_PASSWORD, hasher);

        Mockito.verify(hasher).hash(VALID_PASSWORD);
    }

    @Test
    void givenTooShortPassword_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(TOO_SHORT_PASSWORD, hasher));
    }

    @Test
    void givenPasswordWithoutUppercase_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(MISSING_UPPERCASE, hasher));
    }

    @Test
    void givenPasswordWithoutNumber_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(MISSING_NUMBER, hasher));
    }

    @Test
    void givenPasswordWithoutSpecialCharacter_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(MISSING_SPECIAL_CHAR, hasher));
    }

    @Test
    void givenEmptyPassword_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(StringUtils.EMPTY, hasher));
    }

    @Test
    void givenNullPassword_whenCreatePassword_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> new Password(NULL_PASSWORD, hasher));
    }

    @Test
    void givenTwoPasswordsWithSameRawValue_whenCompare_thenTheyAreEqual() {
        Password password1 = new Password(VALID_PASSWORD, hasher);
        Password password2 = new Password(VALID_PASSWORD, hasher);

        Assertions.assertEquals(password1, password2);
        Assertions.assertEquals(password1.hashCode(), password2.hashCode());
    }
}
