package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;

public class TripPersistenceMapper {
    public TripRecord toRecord(Trip trip) {
        return new TripRecord(
                trip.getTripId(),
                trip.getIdulTraveler(),
                trip.getRidePermitId(),
                trip.getStartAt(),
                trip.getEndAt()
        );
    }
    public Trip toDomain(TripRecord record) {
        return new Trip(
                record.tripId(),
                record.travelerIdul(),
                record.ridePermitId(),
                record.startAt(),
                record.endAt()
        );
    }
}