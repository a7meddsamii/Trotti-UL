package ca.ulaval.glo4003.trotti.infrastructure.communication;

import ca.ulaval.glo4003.trotti.domain.communication.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.communication.values.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.services.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JakartaEmailServiceAdapter implements EmailService {
    private final Session session;

    public JakartaEmailServiceAdapter(Session session) {
        this.session = session;
    }

    @Override
    public void send(EmailMessage emailMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(session.getProperty("FromMail")));
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailMessage.getRecipient().toString()));
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getBody());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email." + e.getMessage());
        }
    }
}
