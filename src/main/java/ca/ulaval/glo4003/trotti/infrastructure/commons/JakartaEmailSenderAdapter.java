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
    private static final String ENCODING = "UTF-8";
    private final String fromEmail;

    public JakartaEmailSenderAdapter(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        Session session = Session.getInstance(JavaMailSenderConfiguration.getInstance());

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());
            Transport.send(message);
        }catch (MessagingException e){
            throw new EmailSendException("Failed to send email.");
        }
    }

    public static void main(String[] args) {
        EmailSender emailSender = new JakartaEmailSenderAdapter("noreply@trottiul.ca");
        EmailRequest emailRequest = new EmailRequest(Email.from("ahmed.sami.1@ulaval.ca"), "test", "hello world");
        emailSender.sendEmail(emailRequest);
    }
}
