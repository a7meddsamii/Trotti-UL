package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment.TransactionCompletedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.EmailMessageFactory;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;

public class CommunicationTransactionCompletedHandler {

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    public CommunicationTransactionCompletedHandler(
            EmailService emailService,
            EmailMessageFactory emailMessageFactory) {
        this.emailService = emailService;
        this.emailMessageFactory = emailMessageFactory;
    }

    public void handle(TransactionCompletedEvent event) {
        Contact contact = Contact.findByIdul(event.getIdul());

        EmailMessage emailMessage = emailMessageFactory.createTransactionCompletedMessage(
                contact.getEmail(), event.getTransactionId(), event.getTransactionStatus(),
                event.getTransactionDescription());

        emailService.send(emailMessage);
    }
}
