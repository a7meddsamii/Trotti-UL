package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;
import java.util.ArrayList;
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
        return tripTable.get(travelerId).stream().map(mapper::toDomain).toList();
    }
}
