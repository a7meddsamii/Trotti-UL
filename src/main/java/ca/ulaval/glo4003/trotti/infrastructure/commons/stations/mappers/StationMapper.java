package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;

public class StationMapper {

    public StationConfiguration toStationConfiguration(StationDataRecord record) {
        final Location stationLocation = Location.of(record.location(), record.name());

        return new StationConfiguration(stationLocation, record.capacity());
    }
}
