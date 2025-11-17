package ca.ulaval.glo4003.trotti.account.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class EmployeAccountCreatedEvent extends Event {

    public EmployeAccountCreatedEvent(Idul idul) {
        super(idul, "account.created.employee");
    }
}
