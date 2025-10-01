package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Traveler {

    private final Idul idul;
    private final Email email;
    private List<RidePermit> activeRidePermits;

    public Traveler(Idul idul, Email email) {
        this.idul = idul;
        this.email = email;
        this.activeRidePermits = new ArrayList<>();
    }

    public List<RidePermit> updateActiveRidePermits(List<RidePermit> ridePermitsHistory) {
        List<RidePermit> activeRidePermits = ridePermitsHistory.stream()
                .filter(ridePermit -> ridePermit.isActiveFor(LocalDate.now())).toList();
        List<RidePermit> newlyActiveRidePermits = ridePermitsHistory.stream()
                .filter(ridePermit -> !this.activeRidePermits.contains(ridePermit)).toList();
        this.activeRidePermits = activeRidePermits;

        return newlyActiveRidePermits;
    }

    public List<RidePermit> getRidePermits() {
        return List.copyOf(activeRidePermits);
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }
}
