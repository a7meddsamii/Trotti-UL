package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import java.util.List;

public class TravelerFixture {

    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Email AN_EMAIL = Email.from("john.doe@ulaval.ca");

    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private List<RidePermit> permits = List.of();

    public TravelerFixture withNoRidePermit() {
        this.permits = List.of();
        return this;
    }

    public TravelerFixture withRidePermit() {
        this.permits = List.of(new RidePermitFixture().build());
        return this;
    }

    public TravelerFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Traveler build() {
        return new Traveler(idul, email, permits);
    }
}
