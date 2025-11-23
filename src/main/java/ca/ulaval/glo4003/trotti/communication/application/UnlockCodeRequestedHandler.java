package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.UnlockCodeRequestedEvent;

public class UnlockCodeRequestedHandler {

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    public UnlockCodeRequestedHandler(EmailService emailService, EmailMessageFactory emailMessageFactory) {
        this.emailService = emailService;
        this.emailMessageFactory = emailMessageFactory;
    }

    public void handle(UnlockCodeRequestedEvent event) {
        Contact contact = Contact.findByIdul(event.getIdul());

        EmailMessage emailMessage = emailMessageFactory.createUnlockCodeMessage(contact.getEmail(),
                contact.getName(), event.getUnlockCode());

        emailService.send(emailMessage);
    }
}
