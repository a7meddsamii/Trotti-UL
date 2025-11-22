package ca.ulaval.glo4003.trotti.trip.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.StationRecord;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationPersistenceMapperTest {

    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final SlotNumber ANOTHER_SLOT_NUMBER = new SlotNumber(2);
    private static final ScooterId ANOTHER_SCOOTER_ID = ScooterId.randomId();
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

        Assertions.assertEquals(station.getLocation(), record.location());
        Assertions.assertEquals(1, record.slots().size());
        Assertions.assertEquals(SCOOTER_ID, record.slots().get(SLOT_NUMBER));
    }

    @Test
    void givenStationRecord_whenToDomain_thenReturnCorrespondingStation() {
        Map<SlotNumber, ScooterId> slots =
                Map.of(SLOT_NUMBER, SCOOTER_ID, ANOTHER_SLOT_NUMBER, ANOTHER_SCOOTER_ID);
        StationRecord record = new StationRecord(StationFixture.A_LOCATION, slots);

        Station station = stationMapper.toDomain(record);

        Assertions.assertEquals(record.location(), station.getLocation());
        asserAllScootersAreInTheCorrectSlotAsPersisted(station, slots);
    }

    private static void asserAllScootersAreInTheCorrectSlotAsPersisted(Station station,
            Map<SlotNumber, ScooterId> slots) {
        slots.forEach((slotNumber, scooterId) -> {
            Assertions.assertEquals(Optional.ofNullable(scooterId),
                    station.getDockingArea().getScooterSlots().get(slotNumber));
        });
    }
}
