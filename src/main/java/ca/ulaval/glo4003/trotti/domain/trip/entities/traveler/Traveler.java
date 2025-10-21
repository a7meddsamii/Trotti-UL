package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripBookException;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Traveler {

    private final Idul idul;
    private final Email email;
    private final RidePermitWallet ridePermitWallet;
    private final TripWallet tripWallet;
    private Trip ongoingTrip;

    public Traveler(
            Idul idul,
            Email email,
            RidePermitWallet ridePermitWallet,
            TripWallet tripWallet) {
        this.idul = idul;
        this.email = email;
        this.ridePermitWallet = ridePermitWallet;
        this.tripWallet = tripWallet;
    }

    public void startTraveling(LocalDateTime startTime, RidePermitId ridePermitId, ScooterId scooterId) {
        Trip startTrip = ridePermitWallet.startTrip(startTime, ridePermitId, scooterId);
        tripWallet.add(startTrip);
    }

    public Trip stopTraveling(LocalDateTime endDateTime) {
        if (Optional.ofNullable(ongoingTrip).isEmpty()) {
            throw new TripBookException("Trip does not exist");
        }
        Trip completedTrip = ongoingTrip.end(endDateTime);
        ongoingTrip = null;
        return completedTrip;
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

    public boolean walletHasPermit(RidePermitId ridePermitId) {
        return ridePermitWallet.hasRidePermit(ridePermitId);
    }

    public boolean hasEmptyWallet() {
        return ridePermitWallet.isEmpty();
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }

}
