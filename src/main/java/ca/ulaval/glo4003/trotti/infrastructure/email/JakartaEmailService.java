package ca.ulaval.glo4003.trotti.infrastructure.email;

import ca.ulaval.glo4003.trotti.domain.commons.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.commons.EmailSender;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.EmailSendException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JakartaEmailService implements EmailSender {
    private final Session session;

    public JakartaEmailService(Session session) {
        this.session = session;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(session.getProperty("FromMail")));
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailMessage.getTo()));
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getBody());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email." + e.getMessage());
        }
    }

}
