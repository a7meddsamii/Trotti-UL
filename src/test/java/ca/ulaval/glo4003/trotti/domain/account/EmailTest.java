package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class EmailTest {

    private static final String INVALID_DOMAIN_EMAIL = "john.david@gmail.com";
    private static final String DOUBLE_AT_EMAIL = "glovac@hon@ulaval.ca";
    private static final String NULL_EMAIL = null;

    @Test
    void givenEmailWithWrongdomain_whenCreateEmail_thenThrowInvalidParameterException() {

        Executable emailCreationAttempt = () -> Email.from(INVALID_DOMAIN_EMAIL);

        Assertions.assertThrows(InvalidParameterException.class, emailCreationAttempt);
    }

    @Test
    void givenEmptyEmail_whenCreateEmail_thenThrowInvalidParameterException() {

        Executable emailCreationAttempt = () -> Email.from(StringUtils.EMPTY);

        Assertions.assertThrows(InvalidParameterException.class, emailCreationAttempt);
    }

    @Test
    void givenNullEmail_whenCreateEmail_thenThrowInvalidParameterException() {

        Executable emailCreationAttempt = () -> Email.from(NULL_EMAIL);

        Assertions.assertThrows(InvalidParameterException.class, emailCreationAttempt);
    }

    @Test
    void givenEmailWithDoubleAt_whenCreateEmail_thenThrowInvalidParameterException() {

        Executable emailCreationAttempt = () -> Email.from(DOUBLE_AT_EMAIL);

        Assertions.assertThrows(InvalidParameterException.class, emailCreationAttempt);
    }
}
