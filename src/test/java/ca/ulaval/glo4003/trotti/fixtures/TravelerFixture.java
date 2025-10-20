package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.TripWallet;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.RidePermitWallet;
import java.util.List;

public class TravelerFixture {

    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Email AN_EMAIL = Email.from("john.doe@ulaval.ca");

    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private RidePermitWallet ridePermitWallet = new RidePermitWallet(List.of());
    private TripWallet tripWallet = new TripWallet(List.of());

    public TravelerFixture withNoRidePermit() {
        this.ridePermitWallet = new RidePermitWallet(List.of());
        return this;
    }

    public TravelerFixture withRidePermit() {
        this.ridePermitWallet.updateActiveRidePermits(List.of(new RidePermitFixture().build()));
        return this;
    }

    public TravelerFixture withoutTrips() {
        this.tripWallet = new TripWallet(List.of());
        return this;
    }

    public TravelerFixture withTrips(Trip trip) {
        this.tripWallet = new TripWallet(List.of(trip));
        return this;
    }

    public TravelerFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Traveler build() {
        return new Traveler(idul, email, ridePermitWallet, tripWallet);
    }
}
