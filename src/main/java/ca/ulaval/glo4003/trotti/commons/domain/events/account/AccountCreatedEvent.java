package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class AccountCreatedEvent extends Event {

    private final String name;
    private final String email;
    private final String role;

    public AccountCreatedEvent(Idul idul, String name, String email, String role) {
        super(idul, "account.created");
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
