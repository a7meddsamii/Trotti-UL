package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripException;

import java.time.LocalDateTime;

public class Trip {

    private final Id id;
    private final LocalDateTime startTime;
    private final Id ridePermit;
    private final Id scooterId;
    private final Idul travelerId;
    private LocalDateTime endTime;



    public Trip(LocalDateTime startTime, Id ridePermit, Idul TravelerIdul,Id scooterId) {
        this.id = Id.randomId();
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = TravelerIdul;
        this.scooterId = scooterId;
    }

    private Trip(Id id, LocalDateTime startTime, Id ridePermit, Idul travelerId, Id scooterId, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = travelerId;
        this.scooterId = scooterId;
        this.endTime = endTime;
    }

    public Trip end(LocalDateTime endTime)  {
        if (this.endTime != null)
            throw new TripException("Cannot finish trip after end time");

        return new Trip(id, startTime, ridePermit, travelerId,scooterId ,endTime);
    }




}
