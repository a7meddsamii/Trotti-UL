package ca.ulaval.glo4003.trotti.trip.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

import java.time.LocalDateTime;

public abstract class TripEvent extends Event {

    private final String ridePermitId;
    private final String tripId;
    private final LocalDateTime startTime;
    private final String startLocation;

    protected TripEvent(Idul idul,
                            String ridePermitId,
                            String tripId,
                            LocalDateTime startTime,
                            String startLocation,
                            String eventType) {
        super(idul, eventType);
        this.ridePermitId = ridePermitId;
        this.tripId = tripId;
        this.startTime = startTime;
        this.startLocation = startLocation;
    }

    public String getRidePermitId() {
        return ridePermitId;
    }

    public String getTripId() {
        return tripId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getStartLocation() {
        return startLocation;
    }
}
