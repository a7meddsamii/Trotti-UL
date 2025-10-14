package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTripRepository implements TripRepository {
    private final Map<Id, TripRecord> tripTable = new ConcurrentHashMap<>();
    private final TripPersistenceMapper mapper;
    
    public InMemoryTripRepository(
            TripPersistenceMapper mapper
    ) {
        this.mapper = mapper;
    }
    
    @Override
    public void save(Trip trip) {
        TripRecord record = mapper.toRecord(trip);
        
        tripTable.put(record.tripId(), record);
    }
}