package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.station.entities.Station;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationRecord;

public class StationMapper {

    public Station toDomain(StationRecord record) {
        return new Station(record.location(), record.name(), record.capacity());
    }
}
