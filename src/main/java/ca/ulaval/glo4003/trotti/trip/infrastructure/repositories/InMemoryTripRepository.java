package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTripRepository implements TripRepository {
    private Map<TripId, TripRecord> tripTable = new HashMap<>();
    private final TripPersistenceMapper mapper;

    public InMemoryTripRepository(TripPersistenceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean exists(Idul idul, TripStatus status) {
        return tripTable.values().stream().anyMatch(tripRecord -> tripRecord.idul().equals(idul)
                && tripRecord.tripStatus().equals(status));

    }

    @Override
    public void save(Trip trip) {
        TripRecord tripRecord = mapper.toRecord(trip);
        tripTable.put(tripRecord.tripId(), tripRecord);
    }

    @Override
    public List<Trip> findAllByIdul(Idul idul) {
        return tripTable.values().stream().filter(tripRecord -> tripRecord.idul().equals(idul))
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Trip> findBy(Idul idul, TripStatus tripStatus) {
        return tripTable.values().stream()
                .filter(tripRecord -> tripRecord.idul().equals(idul)
                        && tripRecord.tripStatus().equals(tripStatus))
                .map(mapper::toDomain).toList();
    }
}
