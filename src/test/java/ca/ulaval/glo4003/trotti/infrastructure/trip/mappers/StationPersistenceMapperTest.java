package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.DockingArea;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationPersistenceMapperTest {

    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private StationPersistenceMapper stationMapper;
    private StationFixture stationFixture;

    @BeforeEach
    void setUp() {
        stationMapper = new StationPersistenceMapper();
        stationFixture = new StationFixture();
    }

    @Test
    void givenStation_whenToRecord_thenReturnCorrespondingStationRecord() {
        Station station = stationFixture.withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID).build();

        StationRecord record = stationMapper.toRecord(station);

        assertEquals(station.getLocation(), record.location());
        assertEquals(station.getDockingArea(), record.dockingArea());
    }

    @Test
    void givenStationRecord_whenToDomain_thenReturnCorrespondingStation() {
        DockingArea dockingArea = stationFixture.withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID).build().getDockingArea();
        StationRecord record = new StationRecord(StationFixture.A_LOCATION, dockingArea);

        Station station = stationMapper.toDomain(record);

        assertEquals(record.location(), station.getLocation());
        assertEquals(record.dockingArea(), station.getDockingArea());
    }

}
