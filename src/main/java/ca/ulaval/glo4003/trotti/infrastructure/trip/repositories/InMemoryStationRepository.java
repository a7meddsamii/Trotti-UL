package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStationRepository implements StationRepository {
    private final Map<Location, StationRecord> stations = new HashMap<>();
    private final StationPersistenceMapper stationMapper;

    public InMemoryStationRepository(StationPersistenceMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    @Override
    public void save(Station station) {
        StationRecord stationRecord = stationMapper.toRecord(station);
        stations.put(station.getLocation(), stationRecord);
    }

    @Override
    public Station findByLocation(Location location) {
        return stationMapper.toDomain(stations.get(location));
    }

}
