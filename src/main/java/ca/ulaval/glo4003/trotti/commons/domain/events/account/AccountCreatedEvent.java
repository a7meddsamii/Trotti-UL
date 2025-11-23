package ca.ulaval.glo4003.trotti.account.domain.events;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class AccountCreatedEvent extends Event {

    private final String name;
    private final String email;

    public AccountCreatedEvent(String name, String email, Idul idul) {
        super(idul, "account.created");
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
