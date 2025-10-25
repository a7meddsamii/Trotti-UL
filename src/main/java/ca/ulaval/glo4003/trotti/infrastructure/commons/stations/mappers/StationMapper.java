package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;

public class StationMapper {

    public StationConfiguration toDomain(StationDataRecord record) {
        return new StationConfiguration(
                record.location(),
                record.name(),
                record.capacity()
        );
    }
}
