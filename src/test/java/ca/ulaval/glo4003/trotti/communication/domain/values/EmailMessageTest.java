package ca.ulaval.glo4003.trotti.communication.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class EmailMessageTest {

    private static final String EMAIL_SUBJECT = "Welcome to TrottiUL";
    private static final String EMAIL_BODY = "Your account has been created successfully.";
    private static final String EMAIL_ADDRESS = "student@ulaval.ca";

    private Email emailRecipient;

    @BeforeEach
    void setup() {
        emailRecipient = Email.from(EMAIL_ADDRESS);
    }

    @Test
    void givenRecipientAndSubject_whenBuildingEmailMessage_thenCreatesMessageWithNullBody() {
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(emailRecipient)
                .withSubject(EMAIL_SUBJECT).build();

        Assertions.assertEquals(emailRecipient, emailMessage.getRecipient());
        Assertions.assertEquals(EMAIL_SUBJECT, emailMessage.getSubject());
        Assertions.assertNull(emailMessage.getBody());
    }

    @Test
    void givenAllValidParameters_whenBuildingEmailMessage_thenCreatesCompleteMessage() {
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(emailRecipient)
                .withSubject(EMAIL_SUBJECT).withBody(EMAIL_BODY).build();

        Assertions.assertEquals(emailRecipient, emailMessage.getRecipient());
        Assertions.assertEquals(EMAIL_SUBJECT, emailMessage.getSubject());
        Assertions.assertEquals(EMAIL_BODY, emailMessage.getBody());
    }

    @Test
    void givenMissingRecipient_whenBuildingEmailMessage_thenThrowsInvalidParameterException() {
        Executable emailCreation = () -> EmailMessage.builder().withSubject(EMAIL_SUBJECT)
                .withBody(EMAIL_BODY).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }

    @Test
    void givenMissingSubject_whenBuildingEmailMessage_thenThrowsInvalidParameterException() {
        Executable emailCreation = () -> EmailMessage.builder().withRecipient(emailRecipient)
                .withBody(EMAIL_BODY).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }
}
