package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;

public class TripPersistenceMapper {
    public TripRecord toRecord(Trip trip) {
        return new TripRecord(trip.getStartTime(), trip.getRidePermitId(), trip.getTravelerIdul(),
                trip.getScooterId(), trip.getEndTime());
    }

    public Trip toDomain(TripRecord record) {
        return new Trip(record.startTime(), record.ridePermitId(), record.travelerIdul(),
                record.scooterId()).end(record.endTime());
    }
}
