package ca.ulaval.glo4003.trotti.account.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class AccountCreatedEvent extends Event {

    private final String name;
    private final String accountType;

    public AccountCreatedEvent(Idul idul, String name, String accountType) {
        super(idul, "account.created." + accountType.toLowerCase());
        this.name = name;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public String getAccountType() {
        return accountType;
    }
}
