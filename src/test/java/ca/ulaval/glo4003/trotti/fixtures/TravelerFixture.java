package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.TripBook;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Wallet;

import java.util.List;

public class TravelerFixture {

    private static final Idul AN_IDUL = Idul.from("JD12345");
    private static final Email AN_EMAIL = Email.from("john.doe@ulaval.ca");

    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private Wallet wallet = new Wallet(List.of());
    private TripBook tripBook = new TripBook(List.of());

    public TravelerFixture withNoRidePermit() {
        this.wallet = new Wallet(List.of());
        return this;
    }

    public TravelerFixture withRidePermit() {
        this.wallet.updateActiveRidePermits(List.of(new RidePermitFixture().build()));
        return this;
    }

    public TravelerFixture withoutTrips() {
        this.tripBook = new TripBook(List.of());
        return this;
    }

    public TravelerFixture withTrips(Trip trip ) {
        this.tripBook = new TripBook(List.of( trip ));
        return this;
    }

    public TravelerFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Traveler build() {
        return new Traveler(idul, email, wallet, tripBook);
    }
}
