package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;

public class RidePermitActivationHandler {

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    public RidePermitActivationHandler(EmailService emailService, EmailMessageFactory emailMessageFactory) {
        this.emailService = emailService;
        this.emailMessageFactory = emailMessageFactory;
    }

    public void handle(RidePermitActivatedEvent event) {
        event.getRidePermitSnapshot().forEach(
                s -> {
                    Contact contact = Contact.findByIdul(s.idul());
                    EmailMessage emailMessage = emailMessageFactory.createRidePermitActivationMessage(contact.getEmail(),
                            s);

                    emailService.send(emailMessage);
                }
        );
    }
}
