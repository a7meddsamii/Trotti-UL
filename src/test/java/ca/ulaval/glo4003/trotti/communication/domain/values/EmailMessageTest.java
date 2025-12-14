package ca.ulaval.glo4003.trotti.communication.domain.values;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class EmailMessageTest {

    private static final String SUBJECT = "a_subject";
    private static final String BODY = "a_body";

    private Email email;

    @BeforeEach
    void setup() {
        this.email = Mockito.mock(Email.class);
    }

    @Test
    void givenOnlyRecipientAndSubject_whenCreatingEmailMessage_thenReturnEmailMessage() {
        EmailMessage emailMessage =
                EmailMessage.builder().withRecipient(email).withSubject(SUBJECT).build();

        Assertions.assertEquals(email, emailMessage.getRecipient());
        Assertions.assertEquals(SUBJECT, emailMessage.getSubject());
        Assertions.assertNull(emailMessage.getBody());
    }

    @Test
    void givenValidParams_whenCreatingEmailMessage_thenReturnEmailMessage() {
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(email)
                .withSubject(SUBJECT).withBody(BODY).build();

        Assertions.assertEquals(email, emailMessage.getRecipient());
        Assertions.assertEquals(SUBJECT, emailMessage.getSubject());
        Assertions.assertEquals(BODY, emailMessage.getBody());
    }

    @Test
    void givenNoRecipient_whenCreatingEmailMessage_thenThrowsException() {
        Executable emailCreation =
                () -> EmailMessage.builder().withBody(BODY).withSubject(SUBJECT).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }

    @Test
    void givenNoSubject_whenCreatingEmailMessage_thenThrowsException() {
        Executable emailCreation =
                () -> EmailMessage.builder().withBody(BODY).withRecipient(email).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }
}
