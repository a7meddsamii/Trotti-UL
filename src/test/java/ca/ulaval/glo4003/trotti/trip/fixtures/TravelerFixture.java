package ca.ulaval.glo4003.trotti.trip.fixtures;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.RidePermitWallet;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
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
