package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;

public class CommunicationOrderPlacedHandler {

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    public CommunicationOrderPlacedHandler(EmailService emailService, EmailMessageFactory emailMessageFactory) {
        this.emailService = emailService;
        this.emailMessageFactory = emailMessageFactory;
    }

    public void handle(OrderPlacedEvent event) {
        Contact contact = Contact.findByIdul(event.getIdul());

        EmailMessage emailMessage = emailMessageFactory.createOrderConfirmationMessage(
                contact.getEmail(), event.getOrderId());

        emailService.send(emailMessage);
    }
}
