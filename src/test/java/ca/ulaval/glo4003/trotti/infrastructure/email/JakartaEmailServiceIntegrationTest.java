package ca.ulaval.glo4003.trotti.infrastructure.email;

import ca.ulaval.glo4003.trotti.domain.commons.Email;
import ca.ulaval.glo4003.trotti.domain.commons.EmailMessage;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JakartaEmailServiceIntegrationTest {

    private static final String HOST = "localhost";
    private static final String PORT = "3025";
    private static final String FROM = "no-reply@test.com";
    private static final String A_SUBJECT = "a_subject";
    private static final String A_BODY = "a_body";
    private static final String A_EMAIL = "JhonDoe@ulaval.ca";

    private static GreenMail greenMail;
    private JakartaEmailService emailService;

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
    void setUp() {
        Session session = Session.getInstance(System.getProperties());
        session.getProperties().put("mail.smtp.host", HOST);
        session.getProperties().put("mail.smtp.port", PORT);
        session.getProperties().put("FromMail", FROM);
        emailService = new JakartaEmailService(session);
    }

    @Test
    void givenAEmailMessage_whenEmailSent_thenEmailSent() throws MessagingException, IOException {
        EmailMessage emailMessage = new EmailMessage(Email.from(A_EMAIL), A_SUBJECT, A_BODY);

        emailService.send(emailMessage);

        MimeMessage[] mimeMessages = greenMail.getReceivedMessages();
        Assertions.assertThat(mimeMessages).hasSize(1);
        MimeMessage receivedMessage = mimeMessages[0];
        Assertions.assertThat(receivedMessage.getSubject()).isEqualTo(A_SUBJECT);
        Assertions.assertThat(receivedMessage.getFrom()[0].toString()).isEqualTo(FROM);
        Assertions.assertThat(receivedMessage.getAllRecipients()[0].toString()).isEqualTo(A_EMAIL);
        Assertions.assertThat(receivedMessage.getContent().toString()).isEqualTo(A_BODY);
    }

}
