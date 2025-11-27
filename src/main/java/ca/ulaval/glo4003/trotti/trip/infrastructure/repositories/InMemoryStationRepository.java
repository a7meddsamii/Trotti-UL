package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.StationRecord;
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
        StationRecord stationRecord = stations.get(location);
        if (stationRecord == null)
            throw new NotFoundException("Station not found at " + location);
        return stationMapper.toDomain(stationRecord);
    }

}
