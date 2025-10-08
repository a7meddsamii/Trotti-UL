package ca.ulaval.glo4003.trotti.infrastructure.commons.communication.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.communication.values.EmailMessage;
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

    private static final String HOST = "localhost";
    private static final String PORT = "3025";
    private static final String FROM = "no-reply@test.com";
    private static final String A_SUBJECT = "a_subject";
    private static final String A_BODY = "a_body";
    private static final String A_EMAIL = "JhonDoe@ulaval.ca";

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
        session.getProperties().put("mail.smtp.host", HOST);
        session.getProperties().put("mail.smtp.port", PORT);
        session.getProperties().put("FromMail", FROM);
        emailService = new JakartaEmailServiceAdapter(session);
    }

    @Test
    void givenAEmailMessage_whenEmailSent_thenEmailSent() throws MessagingException, IOException {
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(Email.from(A_EMAIL))
                .withBody(A_BODY).withSubject(A_SUBJECT).build();

        emailService.send(emailMessage);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals(1, mimeMessages.length);
        MimeMessage receivedMessage = mimeMessages[0];
        Assertions.assertEquals(A_EMAIL, receivedMessage.getAllRecipients()[0].toString());
        Assertions.assertEquals(A_SUBJECT, receivedMessage.getSubject());
        Assertions.assertEquals(A_BODY, receivedMessage.getContent());
    }

}
