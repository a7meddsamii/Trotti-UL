package ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.util.List;

public class RidePermitActivatedEvent extends Event {

    private final List<RidePermitSnapshot> ridePermitSnapshot;

    public RidePermitActivatedEvent(List<RidePermitSnapshot> ridePermitSnapshot) {
        super(null, "ridepermit.activated");
        this.ridePermitSnapshot = ridePermitSnapshot;
    }

    public List<RidePermitSnapshot> getRidePermitSnapshot() {
        return List.copyOf(ridePermitSnapshot);
    }
}
