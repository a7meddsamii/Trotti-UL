package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;

import java.time.LocalDate;

public class Pass {
    private final Id id;
    private final Idul idul;
    private final Session session;

    public Pass(Id id, Idul idul, Session session) {
        this.id = id;
        this.idul = idul;
        this.session = session;
    }

    public boolean isActiveFor(LocalDate date) {
        return session.contains(date);
    }



}
