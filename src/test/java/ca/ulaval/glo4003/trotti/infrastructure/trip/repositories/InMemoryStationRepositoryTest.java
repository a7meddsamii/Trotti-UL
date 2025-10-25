package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.DockingArea;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryStationRepositoryTest {

    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final SlotNumber ANOTHER_SLOT_NUMBER = new SlotNumber(2);
    private static final ScooterId ANOTHER_SCOOTER_ID = ScooterId.randomId();
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

        Optional<Station> retrievedStation =
                stationRepository.findByLocation(station.getLocation());
        Assertions.assertTrue(retrievedStation.isPresent());
        Assertions.assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        assertEqual(station.getDockingArea(), retrievedStation.get().getDockingArea());
    }

    @Test
    void givenNonExistentStation_whenFindByLocation_thenReturnEmptyOptional() {
        Location nonExistentLocation = Location.of("Building", "Name");

        Optional<Station> retrievedStation = stationRepository.findByLocation(nonExistentLocation);

        Assertions.assertTrue(retrievedStation.isEmpty());
    }

    @Test
    void givenExistingStationWithScooterId_whenFindByScooterId_thenReturnStation() {
        Station station = stationFixture.withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID)
                .withOccupiedSlot(ANOTHER_SLOT_NUMBER, ANOTHER_SCOOTER_ID).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        Assertions.assertTrue(retrievedStation.isPresent());
        Assertions.assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        assertEqual(station.getDockingArea(), retrievedStation.get().getDockingArea());
    }

    @Test
    void givenNonExistentStationWithScooterId_whenFindByScooterId_thenReturnEmptyOptional() {
        Station station = stationFixture.withEmptySlot(SLOT_NUMBER).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        Assertions.assertTrue(retrievedStation.isEmpty());
    }

    private static void assertEqual(DockingArea expected, DockingArea actual) {
        expected.getScooterSlots().forEach((slotNumber, slot) -> {
            Assertions.assertEquals(
                    actual.getScooterSlots().get(slotNumber).getDockedScooter().orElse(null),
                    slot.getDockedScooter().orElse(null));
        });

        Assertions.assertEquals(expected.getScooterSlots().size(), actual.getScooterSlots().size());

    }
}
