package ca.ulaval.glo4003.trotti.commons.domain.events.account;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class EmployeAccountCreatedEvent extends Event {

    public EmployeAccountCreatedEvent(Idul idul) {
        super(idul, "account.created.employee");
    }
}
