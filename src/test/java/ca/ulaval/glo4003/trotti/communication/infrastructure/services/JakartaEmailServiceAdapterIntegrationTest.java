package ca.ulaval.glo4003.trotti.communication.infrastructure.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.junit.jupiter.api.*;

class JakartaEmailServiceAdapterIntegrationTest {

    private static final String HOST = "localhost";
    private static final String PORT = "3025";
    private static final String FROM = "no-reply@test.com";
    private static final String SUBJECT = "a_subject";
    private static final String BODY = "a_body";
    private static final String EMAIL = "JhonDoe@ulaval.ca";

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
        EmailMessage emailMessage = EmailMessage.builder().withRecipient(Email.from(EMAIL))
                .withBody(BODY).withSubject(SUBJECT).build();

        emailService.send(emailMessage);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals(1, mimeMessages.length);
        MimeMessage receivedMessage = mimeMessages[0];
        Assertions.assertEquals(EMAIL, receivedMessage.getAllRecipients()[0].toString());
        Assertions.assertEquals(SUBJECT, receivedMessage.getSubject());
        Assertions.assertEquals(BODY, receivedMessage.getContent());
    }

}
