package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.DockingArea;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryStationRepositoryTest {

    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final Location A_LOCATION = Location.of("vachon", "stationX");

    private InMemoryStationRepository stationRepository;
    private StationFixture stationFixture;

    @BeforeEach
    public void setUp() {
        StationPersistenceMapper mapper = new StationPersistenceMapper();
        stationRepository = new InMemoryStationRepository(mapper);
        stationFixture = new StationFixture();
    }

    @Test
    void givenStation_whenSaving_thenItIsSaved() {
        Station station = stationFixture.withLocation(A_LOCATION)
                .withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID).build();

        stationRepository.save(station);

        Station retrievedStation = stationRepository.findByLocation(station.getLocation());
        Assertions.assertEquals(station.getLocation(), retrievedStation.getLocation());
        assertEqual(station.getDockingArea(), retrievedStation.getDockingArea());
    }

    @Test
    void givenExistentStation_whenFindByLocation_thenReturnStation() {
        Station station = stationFixture.withLocation(A_LOCATION)
                .withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID).build();
        stationRepository.save(station);

        Station retrievedStation = stationRepository.findByLocation(station.getLocation());

        Assertions.assertEquals(station.getLocation(), retrievedStation.getLocation());
        assertEqual(station.getDockingArea(), retrievedStation.getDockingArea());
    }

    private static void assertEqual(DockingArea expected, DockingArea actual) {
        expected.getScooterSlots().forEach((slotNumber, expectedScooterId) -> {
            Assertions.assertEquals(expectedScooterId.orElse(null),
                    actual.getScooterSlots().get(slotNumber).orElse(null));
        });

        Assertions.assertEquals(expected.getScooterSlots().size(), actual.getScooterSlots().size());
    }
}
