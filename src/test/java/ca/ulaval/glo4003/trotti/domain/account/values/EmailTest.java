package ca.ulaval.glo4003.trotti.domain.account.values;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class EmailTest {

    private static final String VALID_ULAVAL_EMAIL = "john.david@ulaval.ca";
    private static final String ANOTHER_VALID_ULAVAL_EMAIL = "marie.dupont@ulaval.ca";
    private static final String INVALID_DOMAIN_EMAIL = "john.david@gmail.com";
    private static final String DOUBLE_AT_EMAIL = "glovac@hon@ulaval.ca";
    private static final String NULL_EMAIL = null;

    @Test
    void givenTwoSameValidEmails_whenCompare_thenTheyAreEqual() {
        Email email1 = Email.from(VALID_ULAVAL_EMAIL);
        Email email2 = Email.from(VALID_ULAVAL_EMAIL);

        Assertions.assertEquals(email1, email2);
        Assertions.assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    void givenTwoDifferentValidEmails_whenCompare_thenTheyAreNotEqual() {
        Email email1 = Email.from(VALID_ULAVAL_EMAIL);
        Email email2 = Email.from(ANOTHER_VALID_ULAVAL_EMAIL);

        Assertions.assertNotEquals(email1, email2);
    }

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
