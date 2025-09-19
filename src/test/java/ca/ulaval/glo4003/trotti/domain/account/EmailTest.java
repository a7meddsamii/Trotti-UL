package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;

class EmailTest {
  private static final String VALID_EMAIL = "abd.xxx@ulaval.ca";
  private static final String INVALID_DOMAIN_EMAIL = "john.david@gmail.com";
  private static final String EMPTY_EMAIL = "";
  private static final String NULL_EMAIL = null;
  private static final String INVALID_CHAR_EMAIL = "glo!vachon@ulaval.ca";

  @Test
  void givenValidUlavalEmail_whenCreateEmail_thenSucceeds() {
    Email email = new Email(VALID_EMAIL);

    assertEquals(VALID_EMAIL, email.value());
  }

  @Test
  void givenEmailwithWrongdomain_whenCreateEmail_thenThrowInvalidEmailException() {
    assertThrows(
      InvalidEmailException.class,
      () -> new Email(INVALID_DOMAIN_EMAIL)
    );
  }

  @Test
  void givenEmptyEmail_whenCreateEmail_thenThrowInvalidEmailException() {
    assertThrows(InvalidEmailException.class, () -> new Email(EMPTY_EMAIL));
  }

  @Test
  void givenNullEmail_whenCreateEmail_thenThrowInvalidEmailException() {
    assertThrows(InvalidEmailException.class, () -> new Email(NULL_EMAIL));
  }

  @Test
  void givenEmailWithInvalidCharacters_whenCreateEmail_thenThrowInvalidEmailException() {
    assertThrows(
      InvalidEmailException.class,
      () -> new Email(INVALID_CHAR_EMAIL)
    );
  }
}
