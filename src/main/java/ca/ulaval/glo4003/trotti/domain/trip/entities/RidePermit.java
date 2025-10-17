package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import java.time.LocalDate;
import java.util.Objects;

public class RidePermit {
    private final Id id;
    private final Idul idul;
    private final Session session;

    public RidePermit(Id id, Idul idul, Session session) {
        this.id = id;
        this.idul = idul;
        this.session = session;
    }

    public Id getId() {
        return id;
    }

    public Idul getIdul() {
        return idul;
    }

    public Session getSession() {
        return session;
    }

    public boolean isActiveFor(LocalDate date) {
        return session.contains(date);
    }

    public boolean matches(Id id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RidePermit ridePermit = (RidePermit) o;
        return id.equals(ridePermit.id) && idul.equals(ridePermit.idul)
                && session.equals(ridePermit.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idul, session);
    }

    @Override
    public String toString() {
        return "pass: " + id + "session" + session;
    }
}
