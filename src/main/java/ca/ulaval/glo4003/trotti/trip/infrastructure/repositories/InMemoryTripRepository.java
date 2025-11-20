package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTripRepository implements TripRepository {
    private Map<Idul, List<TripRecord>> tripTable = new HashMap<>();
    private final TripPersistenceMapper mapper;

    public InMemoryTripRepository(TripPersistenceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean exists(Idul idul, TripStatus status) {
        if (tripTable.containsKey(idul)) {
            return tripTable.get(idul).stream()
                    .anyMatch(tripRecord -> tripRecord.tripStatus().equals(status));
        }

        return false;
    }

    @Override
    public void save(Trip trip) {
        TripRecord tripRecord = mapper.toRecord(trip);
        tripTable.computeIfAbsent(tripRecord.idul(), idul -> new ArrayList<>()).add(tripRecord);
    }

    @Override
    public List<Trip> findAllByIdul(Idul idul) {
        if (tripTable.containsKey(idul)) {
            return tripTable.get(idul).stream().map(mapper::toDomain).toList();
        }

        return Collections.emptyList();
    }

    @Override
    public List<Trip> findBy(Idul idul, TripStatus tripStatus) {
        if (tripTable.containsKey(idul)) {
            return tripTable.get(idul).stream()
                    .filter(tripRecord -> tripRecord.tripStatus().equals(tripStatus))
                    .map(mapper::toDomain).toList();
        }

        return Collections.emptyList();
    }
}
