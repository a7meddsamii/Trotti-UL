package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.WalletException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RidePermitWallet {

    private List<RidePermit> activeRidePermits;

    public RidePermitWallet(List<RidePermit> activeRidePermits) {
        this.activeRidePermits = activeRidePermits;
    }

    public List<RidePermit> updateActiveRidePermits(List<RidePermit> ridePermitsHistory) {
        List<RidePermit> activeRidePermits = ridePermitsHistory.stream()
                .filter(ridePermit -> ridePermit.isActiveFor(LocalDate.now())).toList();
        List<RidePermit> newlyActiveRidePermits = activeRidePermits.stream()
                .filter(ridePermit -> !this.activeRidePermits.contains(ridePermit)).toList();
        this.activeRidePermits = activeRidePermits;

        return newlyActiveRidePermits;
    }

    public List<RidePermit> getRidePermits() {
        return List.copyOf(activeRidePermits);
    }

    public boolean hasRidePermit(Id id) {
        return this.activeRidePermits.stream().anyMatch(ridePermit -> ridePermit.matches(id));
    }

    public boolean isEmpty() {
        return this.activeRidePermits.isEmpty();
    }

    public Trip startTrip(LocalDateTime startTime, Id ridePermitId, Id scooterId) {
        RidePermit ridePermit = getRidePermit(ridePermitId);
        return new Trip(startTime, ridePermitId, ridePermit.getIdul(), scooterId);
    }

    private RidePermit getRidePermit(Id ridePermitId) {
        return activeRidePermits.stream()
                .filter((activeRidePermit) -> activeRidePermit.getId().equals(ridePermitId))
                .findFirst().orElseThrow(() -> new WalletException(" Ride permit not found "));
    }
}
