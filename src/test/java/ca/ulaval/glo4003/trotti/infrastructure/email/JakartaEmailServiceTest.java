package ca.ulaval.glo4003.trotti.infrastructure.email;

import ca.ulaval.glo4003.trotti.domain.commons.EmailMessage;
import ca.ulaval.glo4003.trotti.infrastructure.config.JakartaMailServiceConfiguration;
import jakarta.mail.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JakartaEmailServiceTest {

    private static final String A_EMAIL = "trotiul@ulaval.ca";
    private static final String A_SUBJECT = "a_subject";
    private static final String A_BODY = "a_body";

    private JakartaEmailService emailService;
    
    
    @BeforeEach
    void setUp() {
        Session session = JakartaMailServiceConfiguration.getSession();
        emailService = new JakartaEmailService(session);
    }


    @Test
    void givenEmailMessage_whenSendEmail_thenSendEmail() {
        EmailMessage message = Mockito.mock(EmailMessage.class);
        Mockito.when(message.getTo()).thenReturn(A_EMAIL);
        Mockito.when(message.getSubject()).thenReturn(A_SUBJECT);
        Mockito.when(message.getBody()).thenReturn(A_BODY);

        Assertions.assertDoesNotThrow(() -> emailService.sendEmail(message));

        Mockito.verify(message, Mockito.times(1)).getTo();
        Mockito.verify(message, Mockito.times(1)).getSubject();
        Mockito.verify(message, Mockito.times(1)).getBody();


    }



}