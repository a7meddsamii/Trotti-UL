package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class AccountAdvantageRenewalEvent extends Event {
    private final String advantage;

    public AccountAdvantageRenewalEvent(String advantage) {
        super(null, "account.ride_permit.advantage.renewal");
        this.advantage = advantage;
    }

    public String getAdvantage() {
        return advantage;
    }
}
