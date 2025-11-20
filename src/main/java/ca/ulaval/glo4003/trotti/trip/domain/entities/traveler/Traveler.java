package ca.ulaval.glo4003.trotti.trip.domain.entities.traveler;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TravelerException;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Deprecated
public class Traveler {

    private final Idul idul;
    private final Email email;
    private final RidePermitWallet ridePermitWallet;
    private Trip ongoingTrip;

    public Traveler(Idul idul, Email email, RidePermitWallet ridePermitWallet) {
        this.idul = idul;
        this.email = email;
        this.ridePermitWallet = ridePermitWallet;
        this.ongoingTrip = null;
    }

    public Traveler(Idul idul, Email email, RidePermitWallet ridePermitWallet, Trip ongoingTrip) {
        this.idul = idul;
        this.email = email;
        this.ridePermitWallet = ridePermitWallet;
        this.ongoingTrip = ongoingTrip;
    }

    public void startTraveling(LocalDateTime startTime, RidePermitId ridePermitId,
            ScooterId scooterId) {
        if (Optional.ofNullable(ongoingTrip).isPresent()) {
            throw new TravelerException("A trip is already ongoing");
        }
        if (!walletHasPermit(ridePermitId)) {
            throw new NotFoundException("Ride permit not found. ");
        }
        // ongoingTrip = new Trip(startTime, ridePermitId, idul, scooterId);
    }

    public Trip stopTraveling(LocalDateTime endDateTime) {
        if (Optional.ofNullable(ongoingTrip).isEmpty()) {
            throw new NotFoundException("Trip does not exist");
        }
        // Trip completedTrip = ongoingTrip.end(endDateTime);
        return null;
    }

    public List<RidePermit> updateWallet(List<RidePermit> ridePermitsHistory) {
        return ridePermitWallet.updateActiveRidePermits(ridePermitsHistory);
    }

    public List<RidePermit> getWalletPermits() {
        return ridePermitWallet.getRidePermits();
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

    public Optional<Trip> getOngoingTrip() {
        return Optional.ofNullable(ongoingTrip);
    }

    public ScooterId getUsedScooterId() {
        if (Optional.ofNullable(ongoingTrip).isEmpty()) {
            throw new NotFoundException("Traveler is not currently on a trip");
        }
        return ongoingTrip.getScooterId();
    }

}
