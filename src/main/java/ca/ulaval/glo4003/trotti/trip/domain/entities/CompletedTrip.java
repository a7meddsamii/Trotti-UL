package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;

import java.time.Duration;
import java.time.LocalDateTime;

public class CompletedTrip {

    private final TripId tripId;
    private final Idul idul;
    private final LocalDateTime startTime;
    private final Location startLocation;
    private final LocalDateTime endTime;
    private final Location endLocation;

    public CompletedTrip(TripId tripId,
                         Idul idul,
                         LocalDateTime startTime,
                         Location startLocation,
                         LocalDateTime endTime,
                         Location endLocation) {
        this.tripId = tripId;
        this.idul = idul;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.endTime = endTime;
        this.endLocation = endLocation;
    }

    public Duration calculateDuration() {
        return Duration.between(startTime, endTime);
    }

    public TripId getTripId() {
        return tripId;
    }

    public Idul getIdul() {
        return idul;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Location getEndLocation() {
        return endLocation;
    }
}
