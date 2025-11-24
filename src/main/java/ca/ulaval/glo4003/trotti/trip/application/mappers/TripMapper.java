package ca.ulaval.glo4003.trotti.trip.application.mappers;

import ca.ulaval.glo4003.trotti.trip.application.dto.TripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;

public class TripMapper {

    public TripDto toDto(Trip trip)
    {
        return new TripDto(trip.getTripId(),
                trip.getStartLocation(),
                trip.getEndLocation(),
                trip.getStartTime(),
                trip.getEndTime());
    }
}
