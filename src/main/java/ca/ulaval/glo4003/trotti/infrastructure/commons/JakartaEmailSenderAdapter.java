package ca.ulaval.glo4003.trotti.infrastructure.commons;

import ca.ulaval.glo4003.trotti.domain.commons.Email;
import ca.ulaval.glo4003.trotti.domain.commons.EmailRequest;
import ca.ulaval.glo4003.trotti.domain.commons.EmailSender;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.EmailSendException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class JakartaEmailSenderAdapter implements EmailSender {
    private final Session session;

    public JakartaEmailSenderAdapter(Session session) {
        this.session = session;
    }

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(session.getProperty("FromMail")));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());
            Transport.send(message);
        }catch (MessagingException e){
            throw new EmailSendException("Failed to send email.");
        }
    }

    public static void main(String[] args) {
        EmailSender emailSender = new JakartaEmailSenderAdapter(JakartaMailSenderConfiguration.getSession());
        EmailRequest emailRequest = new EmailRequest(Email.from("samy.khalfallah.1@ulaval.ca"), "test", "hello world");
        emailSender.sendEmail(emailRequest);
    }
}
