package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {
    private static final String VALID_EMAIL = "abd.xxx@ulaval.ca";
    private static final String INVALID_DOMAIN_EMAIL = "john.david@gmail.com";
    private static final String DOUBLE_AT_EMAIL = "glovac@hon@ulaval.ca";
    private static final String NULL_EMAIL = null;


    @Test
    void givenValidUlavalEmail_whenCreateEmail_thenSucceeds() {
        Email email = Email.from(VALID_EMAIL);

        Assertions.assertEquals(VALID_EMAIL, email.toString());
    }

    @Test
    void givenEmailwithWrongdomain_whenCreateEmail_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> Email.from(INVALID_DOMAIN_EMAIL));
    }

    @Test
    void givenEmptyEmail_whenCreateEmail_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> Email.from(StringUtils.EMPTY));
    }

    @Test
    void givenNullEmail_whenCreateEmail_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> Email.from(NULL_EMAIL));
    }

    @Test
    void givenEmailWithDoubleAt_whenCreateEmail_thenThrowInvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> Email.from(DOUBLE_AT_EMAIL));
    }
}
