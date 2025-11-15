package ca.ulaval.glo4003.trotti.trip.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import java.time.LocalDateTime;

public class TripStartedEvent extends TripEvent {

    public TripStartedEvent(
            Idul idul,
            String ridePermitId,
            String tripId,
            LocalDateTime startTime,
            String startLocation) {
        super(idul, ridePermitId, tripId, startTime, startLocation, "trip.started");
    }
}
