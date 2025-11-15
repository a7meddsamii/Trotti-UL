package ca.ulaval.glo4003.trotti.trip.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import java.time.LocalDateTime;

public class TripCompletedEvent extends TripEvent {

    private final LocalDateTime endTime;
    private final String endLocation;

    public TripCompletedEvent(
            Idul idul,
            String ridePermitId,
            String tripId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String startLocation,
            String endLocation) {
        super(idul, ridePermitId, tripId, startTime, startLocation, "trip.completed");
        this.endTime = endTime;
        this.endLocation = endLocation;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getEndLocation() {
        return endLocation;
    }
}
