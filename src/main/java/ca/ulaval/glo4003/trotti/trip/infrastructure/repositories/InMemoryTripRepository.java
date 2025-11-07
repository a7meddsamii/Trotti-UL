package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
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
    public void save(Trip trip) {
        TripRecord record = mapper.toRecord(trip);
        tripTable.computeIfAbsent(record.travelerIdul(), travelerId -> new ArrayList<>())
                .add(record);
    }

    public List<Trip> getTravelerTrips(Idul travelerId) {
        if (tripTable.containsKey(travelerId)) {
            return tripTable.get(travelerId).stream().map(mapper::toDomain).toList();
        }
        return Collections.emptyList();
    }
}
