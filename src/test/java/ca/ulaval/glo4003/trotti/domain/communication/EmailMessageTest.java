package ca.ulaval.glo4003.trotti.domain.communication;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.communication.values.EmailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class EmailMessageTest {

    private static final String A_SUBJECT = "a_subject";
    private static final String A_BODY = "a_body";

    private Email email;

    @BeforeEach
    void setup() {
        this.email = Mockito.mock(Email.class);
    }

    @Test
    void givenOnlyRecipientAndSubject_whenCreatingEmailMessage_thenReturnEmailMessage() {
        EmailMessage emailMessage =
                EmailMessage.builder().withRecipient(email).withSubject(A_SUBJECT).build();

        Assertions.assertEquals(email, emailMessage.getRecipient());
        Assertions.assertEquals(A_SUBJECT, emailMessage.getSubject());
        Assertions.assertNull(emailMessage.getBody());
    }

    @Test
    void givenValidParams_whenCreatingEmailMessage_thenReturnEmailMessage() {
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(email)
                .withSubject(A_SUBJECT).withBody(A_BODY).build();

        Assertions.assertEquals(email, emailMessage.getRecipient());
        Assertions.assertEquals(A_SUBJECT, emailMessage.getSubject());
        Assertions.assertEquals(A_BODY, emailMessage.getBody());
    }

    @Test
    void givenNoRecipient_whenCreatingEmailMessage_thenThrowsException() {
        Executable emailCreation =
                () -> EmailMessage.builder().withBody(A_BODY).withSubject(A_SUBJECT).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }

    @Test
    void givenNoSubject_whenCreatingEmailMessage_thenThrowsException() {
        Executable emailCreation =
                () -> EmailMessage.builder().withBody(A_BODY).withRecipient(email).build();

        Assertions.assertThrows(InvalidParameterException.class, emailCreation);
    }
}
