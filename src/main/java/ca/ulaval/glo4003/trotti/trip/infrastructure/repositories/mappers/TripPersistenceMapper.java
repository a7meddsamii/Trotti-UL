package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;

public class TripPersistenceMapper {

    public TripRecord toRecord(Trip trip) {
        return new TripRecord(trip.getTripId(), trip.getIdul(), trip.getRidePermitId(),
                trip.getScooterId(), trip.getStartTime(), trip.getStartLocation(),
                trip.getEndTime(), trip.getEndLocation(), trip.getStatus());
    }

    public Trip toDomain(TripRecord record) {
        return Trip.from(TripId.from(record.tripId().toString()),
                RidePermitId.from(record.ridePermitId().toString()),
                Idul.from(record.idul().toString()), ScooterId.from(record.scooterId().toString()),
                record.startTime(),
                Location.of(record.startLocation().getBuilding(),
                        record.startLocation().getSpotName()),
                record.endTime(),
                Location.of(record.endLocation().getBuilding(), record.endLocation().getSpotName()),
                record.tripStatus());
    }
}
