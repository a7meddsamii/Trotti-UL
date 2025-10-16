package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;

import java.time.LocalDateTime;
import java.util.List;

public class Traveler {

    private final Idul idul;
    private final Email email;
    private final Wallet wallet;
    private final TripBook tripBook;


    public Traveler(Idul idul, Email email, Wallet wallet, TripBook tripBook) {
        this.idul = idul;
        this.email = email;
        this.wallet = wallet;
        this.tripBook = tripBook;
    }

    public void startTraveling(LocalDateTime startTime, Id ridePermitId, Id scooterId) {
        Trip startTrip = wallet.startTrip(startTime,ridePermitId,scooterId);
        tripBook.add(startTrip);
    }

    public Trip endTraveling(Id trip,LocalDateTime endDateTime) {
        return tripBook.endTrip(trip,endDateTime);
    }

    public List<RidePermit> updateWallet(List<RidePermit> ridePermitsHistory) {
        return wallet.updateActiveRidePermits(ridePermitsHistory);
    }

    public List<RidePermit> getWalletPermits() {
        return wallet.getRidePermits();
    }

    public List<Trip> getBookTrips() {
        return tripBook.getTrips();
    }

    public boolean hasEmptyWallet() {
        return wallet.hasActiveRidePermits();
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }


}
