package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.station.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.station.entities.StationLocation;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationRecord;

public class StationMapper {

    public Station toDomain(StationRecord record) {
        final StationLocation stationLocation = new StationLocation(record.location(), record.name());

        return new Station(stationLocation, record.capacity());
    }
}
