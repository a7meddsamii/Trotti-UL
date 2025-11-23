package ca.ulaval.glo4003.trotti.account.domain.events;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public class EmployeAccountCreatedEvent extends AccountCreatedEvent {

    public EmployeAccountCreatedEvent(Idul idul, String name, String email) {
        super(name, email, idul);
    }
}
