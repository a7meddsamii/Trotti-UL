package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.communication.domain.values.ContactRole;

public class CommunicationAccountCreatedHandler {

    public void handle(AccountCreatedEvent event) {
        Contact contact =
                new Contact(event.getIdul(),
                        event.getName(),
                        Email.from(event.getEmail()),
                        ContactRole.valueOf(event.getRole().toUpperCase()));
        contact.save();
    }
}
