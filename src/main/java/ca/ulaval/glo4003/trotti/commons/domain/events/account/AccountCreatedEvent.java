package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.util.Collections;
import java.util.List;

public class AccountCreatedEvent extends Event {

    private final String name;
    private final String email;
    private final String role;
    private final List<String> advantages;

    public AccountCreatedEvent(
            Idul idul,
            String name,
            String email,
            String role,
            List<String> advantages) {
        super(idul, "account.created");
        this.name = name;
        this.email = email;
        this.role = role;
        this.advantages = advantages;
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

    public List<String> getAdvantages() {
        return Collections.unmodifiableList(advantages);
    }
}
