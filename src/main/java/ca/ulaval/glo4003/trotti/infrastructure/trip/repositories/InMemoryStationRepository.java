package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Station> findByLocation(Location location) {
        return Optional.ofNullable(stations.get(location))
            .map(stationMapper::toDomain);
    }

    @Override
    public Optional<Station> findByScooterId(Id scooterId) {
        return stations.values().stream()
            .filter(record ->  record.dockedScooters().contains(scooterId))
            .findFirst()
            .map(stationMapper::toDomain);
    }
}
