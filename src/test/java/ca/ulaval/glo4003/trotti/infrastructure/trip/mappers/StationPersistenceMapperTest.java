package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class StationPersistenceMapperTest {

    private StationPersistenceMapper stationMapper;
    private StationFixture stationFixture;

    @BeforeEach
    void setUp() {
        stationMapper = new StationPersistenceMapper();
        stationFixture = new StationFixture();
    }

    @Test
    void givenStation_whenToRecord_thenReturnCorrespondingStationRecord() {
        Station station = stationFixture.build();

        StationRecord record = stationMapper.toRecord(station);

        assertEquals(station.getLocation(), record.location());
        assertEquals(station.getDockedScooters(), record.dockedScooters());
        assertEquals(station.getCapacity(), record.capacity());
    }

    @Test
    void givenStationRecord_whenToDomain_thenReturnCorrespondingStation() {
        StationRecord record = new StationRecord(StationFixture.A_LOCATION, StationFixture.DOCKED_SCOOTERS, StationFixture.A_CAPACITY);

        Station station = stationMapper.toDomain(record);

        assertEquals(record.location(), station.getLocation());
        assertEquals(record.dockedScooters(), station.getDockedScooters());
        assertEquals(record.capacity(), station.getCapacity());
    }

}