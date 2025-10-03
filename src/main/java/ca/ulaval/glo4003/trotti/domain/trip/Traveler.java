package ca.ulaval.glo4003.trotti.domain.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class Traveler {

    private final Idul idul;
    private final Email email;
    private List<RidePermit> activeRidePermits;

    public Traveler(Idul idul, Email email, List<RidePermit> activeRidePermits) {
        this.idul = idul;
        this.email = email;
        this.activeRidePermits = activeRidePermits;
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

    public boolean hasActiveRidePermits() {
        return !this.activeRidePermits.isEmpty();
    }

    public Idul getIdul() {
        return idul;
    }

    public Email getEmail() {
        return email;
    }
}
