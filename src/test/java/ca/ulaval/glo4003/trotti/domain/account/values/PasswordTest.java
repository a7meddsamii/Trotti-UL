package ca.ulaval.glo4003.trotti.domain.account.values;

import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
        Mockito.when(hasher.hash(Mockito.anyString())).thenReturn(HASHED_PASSWORD);
    }

    @Test
    void givenValidPassword_whenCreatePassword_thenHasherIsCalled() {
        new Password(VALID_PASSWORD, hasher);

        Mockito.verify(hasher).hash(VALID_PASSWORD);
    }

    @Test
    void givenTooShortPassword_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(TOO_SHORT_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }

    @Test
    void givenPasswordWithoutUppercase_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(MISSING_UPPERCASE, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }

    @Test
    void givenPasswordWithoutNumber_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(MISSING_NUMBER, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }

    @Test
    void givenPasswordWithoutSpecialCharacter_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(MISSING_SPECIAL_CHAR, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }

    @Test
    void givenEmptyPassword_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(StringUtils.EMPTY, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }

    @Test
    void givenNullPassword_whenCreatePassword_thenThrowInvalidParameterException() {

        Executable passwordCreation = () -> new Password(NULL_PASSWORD, hasher);

        Assertions.assertThrows(InvalidParameterException.class, passwordCreation);
    }
}
