package ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.util.List;

public class ActivatedRidePermitEvent extends Event {
    private final List<RidePermitSnapshot> ridePermits;

    public ActivatedRidePermitEvent(List<RidePermitSnapshot> ridePermits) {
        super(null, "ridepermit.activated");
        this.ridePermits = ridePermits;
    }

    public List<RidePermitSnapshot> getRidePermits() {
        return ridePermits;
    }
}
