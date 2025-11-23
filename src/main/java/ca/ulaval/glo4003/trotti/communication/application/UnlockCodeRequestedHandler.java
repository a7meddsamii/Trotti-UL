package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.config.events.handlers.EventHandler;
import ca.ulaval.glo4003.trotti.trip.domain.events.UnlockCodeRequestedEvent;

public class UnlockCodeRequestedHandler implements EventHandler<UnlockCodeRequestedEvent> {

    private final EmailService emailService;

    public UnlockCodeRequestedHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void handle(UnlockCodeRequestedEvent event) {
        Contact contact = Contact.findByIdul(event.getIdul());

        EmailMessage emailMessage = EmailMessage.builder().withSubject("Unlock Code for your trip")
                .withBody("Hello " + contact.getName() + ", \n"
                        + "Here is your code to unlock your scooter : \n" + event.getUnlockCode()
                        + "\n" + "Have a safe ride!\n")
                .withRecipient(contact.getEmail()).build();

        emailService.send(emailMessage);
    }
}
