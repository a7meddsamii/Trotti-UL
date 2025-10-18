package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;

public class StationPersistenceMapper {

    public Station toDomain(StationRecord stationRecord) {
        return new Station(stationRecord.location(), stationRecord.dockedScooters(),
                stationRecord.capacity());
    }

    public StationRecord toRecord(Station station) {
        return new StationRecord(station.getLocation(), station.getDockedScooters(),
                station.getCapacity());
    }
}
