package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;

public class Trip {

    private final LocalDateTime startTime;
    private final RidePermitId ridePermitId;
    private final ScooterId scooterId;
    private final Idul travelerId;
    private final LocalDateTime endTime;

    public Trip(
            LocalDateTime startTime,
            RidePermitId ridePermitId,
            Idul TravelerIdul,
            ScooterId scooterId) {
        this.startTime = startTime;
        this.ridePermitId = ridePermitId;
        this.travelerId = TravelerIdul;
        this.scooterId = scooterId;
        this.endTime = null;
    }

    private Trip(
            LocalDateTime startTime,
            RidePermitId ridePermitId,
            Idul travelerId,
            ScooterId scooterId,
            LocalDateTime endTime) {
        this.startTime = startTime;
        this.ridePermitId = ridePermitId;
        this.travelerId = travelerId;
        this.scooterId = scooterId;
        this.endTime = endTime;
    }

    public Trip end(LocalDateTime endTime) {
        if (this.endTime != null)
            throw new TripException("Cannot finish trip that has already ended");
        if (endTime.isBefore(this.startTime))
            throw new TripException("End time cannot be before start time");

        return new Trip(startTime, ridePermitId, travelerId, scooterId, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public RidePermitId getRidePermitId() {
        return ridePermitId;
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
