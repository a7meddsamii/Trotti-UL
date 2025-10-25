package ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
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
    void givenStationDataRecord_whenToDomain_thenReturnsCorrectStationInitializationData() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);

        StationConfiguration result = stationMapper.toDomain(record);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(A_BUILDING, result.building());
        Assertions.assertEquals(A_SPOT_NAME, result.spotName());
        Assertions.assertEquals(A_CAPACITY, result.capacity());
    }
}
