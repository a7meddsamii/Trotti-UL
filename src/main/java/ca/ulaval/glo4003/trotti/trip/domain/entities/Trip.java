package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Trip {

    private final TripId tripId;
    private final Idul idul;
    private final RidePermitId ridePermitId;
    private final ScooterId scooterId;
    private final LocalDateTime startTime;
    private final Location startLocation;
    private LocalDateTime endTime;
    private Location endLocation;
    private TripStatus tripStatus;

    private Trip(Idul idul,
                 RidePermitId ridePermitId,
                 ScooterId scooterId,
                 LocalDateTime startTime,
                 Location startLocation) {
        this.tripId = TripId.randomId();
        this.idul = idul;
        this.ridePermitId = ridePermitId;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.tripStatus = TripStatus.ONGOING;
    }

    private Trip(TripId tripId,
                 Idul idul,
                 RidePermitId ridePermitId,
                 ScooterId scooterId,
                 LocalDateTime startTime,
                 Location startLocation,
                 LocalDateTime endTime,
                 Location endLocation,
                 TripStatus tripStatus) {
        this.tripId = tripId;
        this.idul = idul;
        this.ridePermitId = ridePermitId;
        this.scooterId = scooterId;
        this.startTime = startTime;
        this.startLocation = startLocation;
        this.endTime = endTime;
        this.endLocation = endLocation;
        this.tripStatus = tripStatus;
    }

    public static Trip start(RidePermitId ridePermitId,
                             Idul idul,
                             ScooterId scooterId,
                             LocalDateTime startTime,
                             Location startLocation) {
        return new Trip(idul, ridePermitId, scooterId, startTime, startLocation);
    }

    public static Trip from(TripId tripId,
                            RidePermitId ridePermitId,
                            Idul idul,
                            ScooterId scooterId,
                            LocalDateTime startTime,
                            Location startLocation,
                            LocalDateTime endTime,
                            Location endLocation,
                            TripStatus tripStatus) {
        return new Trip(tripId,idul, ridePermitId, scooterId, startTime, startLocation, endTime, endLocation, tripStatus);
    }

    public void complete(Location endLocation, LocalDateTime endTime) {
        if (endTime.isBefore(this.startTime))
            throw new TripException("Trip end time cannot be before start time.");

        if (this.tripStatus == TripStatus.COMPLETED)
            throw new TripException("Trip is already completed.");

        this.endLocation = endLocation;
        this.endTime = endTime;
        this.tripStatus = TripStatus.COMPLETED;
    }

    public Duration calculateDuration() {
        if (tripStatus == TripStatus.ONGOING)
            throw new TripException("Cannot calculate duration of an unfinished trip.");

        return Duration.between(startTime, endTime);
    }

    public TripId getTripId() {
        return tripId;
    }

    public Idul getIdul() {
        return idul;
    }

    public RidePermitId getRidePermitId() {
        return ridePermitId;
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

    public TripStatus getStatus() {
        return tripStatus;
    }
}
