package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import java.time.LocalDate;
import java.util.Objects;

public class RidePermit {
    private final RidePermitId id;
    private final Idul idul;
    private final Session session;

    public RidePermit(RidePermitId id, Idul idul, Session session) {
        this.id = id;
        this.idul = idul;
        this.session = session;
    }

    public RidePermitId getId() {
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

    public boolean matches(RidePermitId id) {
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
