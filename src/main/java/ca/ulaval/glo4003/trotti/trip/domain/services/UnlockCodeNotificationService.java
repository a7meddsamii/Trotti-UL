package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;

@Deprecated
public class UnlockCodeNotificationService implements NotificationService<UnlockCode> {

    private final EmailService emailService;

    public UnlockCodeNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(Email recipient, UnlockCode content) {
        try {
            EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                    .withSubject("Unlock Code for your trip").withBody(content.getCode()).build();
            emailService.send(message);
        } catch (EmailSendException ignored) {
        }
    }
}
