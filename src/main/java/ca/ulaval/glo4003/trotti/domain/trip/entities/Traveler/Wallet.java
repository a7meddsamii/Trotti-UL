package ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;

import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Wallet {

    private List<RidePermit> activeRidePermits;

    public Wallet(List<RidePermit> activeRidePermits) {
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
        return Collections.unmodifiableList(activeRidePermits);
    }

    public boolean hasActiveRidePermits() {
        return !this.activeRidePermits.isEmpty();
    }

}
