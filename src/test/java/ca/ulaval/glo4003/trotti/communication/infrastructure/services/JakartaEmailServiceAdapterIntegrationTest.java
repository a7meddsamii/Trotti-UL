package ca.ulaval.glo4003.trotti.communication.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JakartaEmailServiceAdapterIntegrationTest {

    private static final String SMTP_HOST = "localhost";
    private static final String SMTP_PORT = "3025";
    private static final String FROM_EMAIL = "no-reply@test.com";
    private static final String EMAIL_SUBJECT = "Account Creation Confirmation";
    private static final String EMAIL_BODY = "Welcome to TrottiUL! Your account has been created.";
    private static final String RECIPIENT_EMAIL = "student@ulaval.ca";
    private static final int EXPECTED_MESSAGE_COUNT = 1;

    private static GreenMail greenMail;
    private JakartaEmailServiceAdapter emailService;

    @BeforeAll
    static void setUpClass() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterAll
    static void tearDownClass() {
        greenMail.stop();
    }

    @BeforeEach
    void setup() {
        Session session = Session.getInstance(System.getProperties());
        session.getProperties().put("mail.smtp.host", SMTP_HOST);
        session.getProperties().put("mail.smtp.port", SMTP_PORT);
        session.getProperties().put("FromMail", FROM_EMAIL);

        emailService = new JakartaEmailServiceAdapter(session);
    }

    @Test
    void givenValidEmailMessage_whenSendingEmail_thenEmailIsDeliveredCorrectly()
            throws MessagingException, IOException {
        EmailMessage emailMessage =
                EmailMessage.builder().withRecipient(Email.from(RECIPIENT_EMAIL))
                        .withSubject(EMAIL_SUBJECT).withBody(EMAIL_BODY).build();

        emailService.send(emailMessage);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];

        Assertions.assertEquals(EXPECTED_MESSAGE_COUNT, receivedMessages.length);
        Assertions.assertEquals(RECIPIENT_EMAIL, receivedMessage.getAllRecipients()[0].toString());
        Assertions.assertEquals(EMAIL_SUBJECT, receivedMessage.getSubject());
        Assertions.assertEquals(EMAIL_BODY, receivedMessage.getContent());
    }
}
