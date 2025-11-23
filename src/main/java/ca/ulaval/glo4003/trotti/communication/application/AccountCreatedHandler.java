package ca.ulaval.glo4003.trotti.communication.application;

import ca.ulaval.glo4003.trotti.account.domain.events.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.entities.Contact;
import ca.ulaval.glo4003.trotti.config.events.handlers.EventHandler;

public class AccountCreatedHandler implements EventHandler<AccountCreatedEvent> {

    @Override
    public void handle(AccountCreatedEvent event) {
        Contact contact =
                new Contact(event.getIdul(), event.getName(), Email.from(event.getName()));
        contact.save();
    }
}
