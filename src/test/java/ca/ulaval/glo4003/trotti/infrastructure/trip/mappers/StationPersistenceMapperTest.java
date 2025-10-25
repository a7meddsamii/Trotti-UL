package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.StationRecord;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
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
        assertEquals(1, record.slots().size());
        assertEquals(SCOOTER_ID, record.slots().get(SLOT_NUMBER));
    }

    @Test
    void givenStationRecord_whenToDomain_thenReturnCorrespondingStation() {
        Map<SlotNumber, ScooterId> slots = Map.of(SLOT_NUMBER, SCOOTER_ID);
        StationRecord record = new StationRecord(StationFixture.A_LOCATION, slots);

        Station station = stationMapper.toDomain(record);

        assertEquals(record.location(), station.getLocation());
        asserAllScootersAreInTheCorrectSlotAsPersisted(station, slots);
    }

    private static void asserAllScootersAreInTheCorrectSlotAsPersisted(Station station,
            Map<SlotNumber, ScooterId> slots) {
        station.getDockingArea().getScooterSlots().forEach((slotNumber, slot) -> {
            Assertions.assertEquals(slots.get(slotNumber), slot.getDockedScooter().orElse(null));
        });
    }
}
