package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationRecord;

public class StationMapper {

    public Station toDomain(StationRecord record) {
        final Location stationLocation = Location.of(record.location(), record.name());

        return new Station(stationLocation, record.capacity());
    }
}
