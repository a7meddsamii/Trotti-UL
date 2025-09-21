package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {
    private static final String VALID_EMAIL = "abd.xxx@ulaval.ca";
    private static final String INVALID_DOMAIN_EMAIL = "john.david@gmail.com";
    private static final String EMPTY_EMAIL = "";
    private static final String NULL_EMAIL = null;
    private static final String INVALID_CHAR_EMAIL = "glovac@hon@ulaval.ca";

    @Test
    void givenValidUlavalEmail_whenCreateEmail_thenSucceeds() {
        Email email = Email.from(VALID_EMAIL);

        Assertions.assertEquals(VALID_EMAIL, email.toString());
    }

    @Test
    void givenEmailwithWrongdomain_whenCreateEmail_thenThrowInvalidEmailException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> Email.from(INVALID_DOMAIN_EMAIL));
    }

    @Test
    void givenEmptyEmail_whenCreateEmail_thenThrowInvalidEmailException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> Email.from(EMPTY_EMAIL));
    }

    @Test
    void givenNullEmail_whenCreateEmail_thenThrowInvalidEmailException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> Email.from(NULL_EMAIL));
    }

    @Test
    void givenEmailWithInvalidCharacters_whenCreateEmail_thenThrowInvalidEmailException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> Email.from(INVALID_CHAR_EMAIL));
    }
}
