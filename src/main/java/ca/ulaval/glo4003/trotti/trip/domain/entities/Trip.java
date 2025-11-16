package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;

import java.time.Duration;
import java.time.LocalDateTime;

public class Trip {

    private final TripId tripId;
    private final RidePermitId ridePermitId;
    private final Idul idul;
    private final ScooterId scooterId;
    private final LocalDateTime startTime;
    private final Location startLocation;
    private LocalDateTime endTime;
    private Location endLocation;

    private Trip(RidePermitId ridePermitId,
                 Idul idul,
                 ScooterId scooterId,
                 LocalDateTime startTime,
                 Location startLocation) {
        this.tripId = TripId.randomId();
        this.ridePermitId = ridePermitId;
        this.idul = idul;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.startLocation = startLocation;
    }

    private Trip(TripId tripId,
                 RidePermitId ridePermitId,
                 Idul idul,
                 ScooterId scooterId,
                 LocalDateTime startTime,
                 Location startLocation,
                 LocalDateTime endTime,
                 Location endLocation) {
        this.tripId = tripId;
        this.ridePermitId = ridePermitId;
        this.idul = idul;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.endTime = endTime;
        this.endLocation = endLocation;
    }

    public static Trip start(RidePermitId ridePermitId,
                             Idul idul,
                             ScooterId scooterId,
                             LocalDateTime startTime,
                             Location startLocation) {
        return new Trip(ridePermitId, idul, scooterId, startTime, startLocation);
    }

    public static Trip from(TripId tripId,
                            RidePermitId ridePermitId,
                            Idul idul,
                            ScooterId scooterId,
                            LocalDateTime startTime,
                            Location startLocation,
                            LocalDateTime endTime,
                            Location endLocation) {
        return new Trip(tripId, ridePermitId, idul, scooterId, startTime, startLocation, endTime, endLocation);
    }

    public void complete(Location endLocation, LocalDateTime endTime) {
        if (endTime.isBefore(this.startTime))
            throw new TripException("Trip end time cannot be before start time.");

        this.endLocation = endLocation;
        this.endTime = endTime;
    }

    public Duration calculateDuration() {
        return Duration.between(startTime, endTime);
    }

    public TripId getTripId() {
        return tripId;
    }

    public RidePermitId getRidePermitId() {
        return ridePermitId;
    }

    public Idul getIdul() {
        return idul;
    }

    public ScooterId getScooterId() {
        return scooterId;
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
