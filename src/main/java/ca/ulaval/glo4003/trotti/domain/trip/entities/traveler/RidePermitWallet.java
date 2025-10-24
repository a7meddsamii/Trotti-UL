package ca.ulaval.glo4003.trotti.domain.trip.entities.traveler;

import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;

import java.time.LocalDate;
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

    public boolean hasRidePermit(RidePermitId id) {
        return this.activeRidePermits.stream().anyMatch(ridePermit -> ridePermit.matches(id));
    }

    public boolean isEmpty() {
        return this.activeRidePermits.isEmpty();
    }

}
