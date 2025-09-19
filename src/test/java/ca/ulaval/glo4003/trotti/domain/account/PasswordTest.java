package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;

class PasswordTest {
  private static final String VALID_PASSWORD = "StrongPass1!";
  private static final String TOO_SHORT_PASSWORD = "Ab1!";
  private static final String MISSING_UPPERCASE = "strongpass1!";
  private static final String MISSING_NUMBER = "StrongPass!";
  private static final String MISSING_SPECIAL_CHAR = "StrongPass1";
  private static final String EMPTY_PASSWORD = "";
  private static final String NULL_PASSWORD = null;

  @Test
  void givenValidPassword_whenCreatePassword_thenSucceeds() {
    Password password = new Password(VALID_PASSWORD);

    assertEquals(VALID_PASSWORD, password.value());
  }

  @Test
  void givenTooShortPassword_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(TOO_SHORT_PASSWORD)
    );
  }

  @Test
  void givenPasswordWithoutUppercase_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(MISSING_UPPERCASE)
    );
  }

  @Test
  void givenPasswordWithoutNumber_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(MISSING_NUMBER)
    );
  }

  @Test
  void givenPasswordWithoutSpecialCharacter_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(MISSING_SPECIAL_CHAR)
    );
  }

  @Test
  void givenEmptyPassword_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(EMPTY_PASSWORD)
    );
  }

  @Test
  void givenNullPassword_whenCreatePassword_thenThrowInvalidPasswordException() {
    assertThrows(
      InvalidPasswordException.class,
      () -> new Password(NULL_PASSWORD)
    );
  }
}
