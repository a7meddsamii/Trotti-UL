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
    private final RidePermitWallet ridePermitWallet;
    private final TripWallet tripWallet;

    public Traveler(Idul idul, Email email, RidePermitWallet ridePermitWallet, TripWallet tripWallet) {
        this.idul = idul;
        this.email = email;
        this.ridePermitWallet = ridePermitWallet;
        this.tripWallet = tripWallet;
    }

    public Id startTraveling(LocalDateTime startTime, Id ridePermitId, Id scooterId) {
        Trip startTrip = ridePermitWallet.startTrip(startTime, ridePermitId, scooterId);
        tripWallet.add(startTrip);
        return startTrip.getId();
    }

    public Trip stopTraveling(Id tripId, LocalDateTime endDateTime) {
        return tripWallet.endTrip(tripId, endDateTime);
    }

    public List<RidePermit> updateWallet(List<RidePermit> ridePermitsHistory) {
        return ridePermitWallet.updateActiveRidePermits(ridePermitsHistory);
    }

    public List<RidePermit> getWalletPermits() {
        return ridePermitWallet.getRidePermits();
    }

    public List<Trip> getBookTrips() {
        return tripWallet.getTrips();
    }

    public boolean walletHasPermit(Id ridePermitId) {
        return ridePermitWallet.hasRidePermit(ridePermitId);
    }

    public boolean hasEmptyWallet() {
        return !ridePermitWallet.hasActiveRidePermits();
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }

}
