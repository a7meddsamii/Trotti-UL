package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripException;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.time.LocalDateTime;

public class Trip {

    private final LocalDateTime startTime;
    private final RidePermitId ridePermit;
    private final ScooterId scooterId;
    private final Idul travelerId;
    private LocalDateTime endTime;

    public Trip(
            LocalDateTime startTime,
            RidePermitId ridePermit,
            Idul TravelerIdul,
            ScooterId scooterId) {
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = TravelerIdul;
        this.scooterId = scooterId;
    }

    private Trip(
            LocalDateTime startTime,
            RidePermitId ridePermit,
            Idul travelerId,
            ScooterId scooterId,
            LocalDateTime endTime) {
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = travelerId;
        this.scooterId = scooterId;
        this.endTime = endTime;
    }

    public Trip end(LocalDateTime endTime) {
        if (this.endTime != null)
            throw new TripException("Cannot finish trip that has already ended");
        if (endTime.isBefore(this.startTime))
            throw new TripException("End time cannot be before start time");

        return new Trip(startTime, ridePermit, travelerId, scooterId, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public RidePermitId getRidePermit() {
        return ridePermit;
    }

    public ScooterId getScooterId() {
        return scooterId;
    }

    public Idul getTravelerIdul() {
        return travelerId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

}
