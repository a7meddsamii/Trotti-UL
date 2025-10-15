package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripException;

import java.time.LocalDateTime;

public class Trip {

    private final Id id;
    private final LocalDateTime startTime;
    private final Id ridePermit;
    private final Idul travelerId;
    private LocalDateTime endTime;



    public Trip(LocalDateTime startTime, Id ridePermit, Idul TravelerIdul) {
        this.id = Id.randomId();
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = TravelerIdul;
    }

    private Trip(Id id, LocalDateTime startTime, Id ridePermit, Idul travelerId, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.ridePermit = ridePermit;
        this.travelerId = travelerId;
        this.endTime = endTime;
    }

    public Trip end(LocalDateTime endTime)  {
        if (this.endTime != null)
            throw new TripException("Cannot finish trip after end time");

        return new Trip(id, startTime, ridePermit, travelerId ,endTime);
    }




}
