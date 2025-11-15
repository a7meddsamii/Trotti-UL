package ca.ulaval.glo4003.trotti.trip.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.time.LocalDateTime;

public class TripCompletedEvent extends Event {

    private final String ridePermitId;
    private final String tripId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String startLocation;
    private final String endLocation;

    public TripCompletedEvent(
            Idul idul,
            String ridePermitId,
            String tripId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String startLocation,
            String endLocation) {
        super(idul, "trip.completed");
        this.ridePermitId = ridePermitId;
        this.tripId = tripId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }
}
