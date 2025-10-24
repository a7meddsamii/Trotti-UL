package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.RidePermitWallet;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import java.util.List;

public class TravelerFixture {

    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Email AN_EMAIL = Email.from("john.doe@ulaval.ca");

    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private RidePermitWallet ridePermitWallet = new RidePermitWallet(List.of());
    private Trip trip = null;

    public TravelerFixture withNoRidePermit() {
        this.ridePermitWallet = new RidePermitWallet(List.of());
        return this;
    }

    public TravelerFixture withRidePermit() {
        this.ridePermitWallet.updateActiveRidePermits(List.of(new RidePermitFixture().build()));
        return this;
    }

    public TravelerFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public TravelerFixture withTrip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public TravelerFixture withNoTrip() {
        this.trip = null;
        return this;
    }

    public Traveler build() {
        return new Traveler(idul, email, ridePermitWallet, trip);
    }
}
