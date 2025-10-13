package ca.ulaval.glo4003.trotti.domain.trip.communication.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.communication.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.communication.values.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;

public class UnlockCodeNotificationService implements NotificationService<UnlockCode> {

    private final EmailService emailService;

    public UnlockCodeNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(Email recipient, UnlockCode content) {
        try {
            EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                    .withSubject("Unlock Code for your pass" + content.getRidePermitId()).withBody(content.getCode()).build();
            emailService.send(message);
        } catch (EmailSendException ignored) {
        }
    }
}
