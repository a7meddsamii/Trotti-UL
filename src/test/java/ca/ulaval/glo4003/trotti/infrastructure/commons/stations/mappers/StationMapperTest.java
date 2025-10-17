package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationMapperTest {

    private static final String A_BUILDING = "PEPS";
    private static final String A_SPOT_NAME = "Station A";
    private static final int A_CAPACITY = 10;

    private StationMapper stationMapper;

    @BeforeEach
    void setup() {
        stationMapper = new StationMapper();
    }

    @Test
    void givenStationRecord_whenToDomain_thenReturnsCorrectStation() {
        StationDataRecord stationDataRecord = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);

        Station station = stationMapper.toDomain(stationDataRecord);

        Assertions.assertEquals(A_BUILDING, station.getStationLocation().getBuilding());
        Assertions.assertEquals(A_SPOT_NAME, station.getStationLocation().getSpotName());
        Assertions.assertEquals(A_CAPACITY, station.getCapacity());
    }
}
