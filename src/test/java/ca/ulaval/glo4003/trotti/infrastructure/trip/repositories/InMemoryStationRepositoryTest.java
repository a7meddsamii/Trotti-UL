package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.ScooterSlot;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import java.util.Optional;
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
        assertTrue(retrievedStation.isPresent());
        assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        ScooterSlot retrievedSlot =
                retrievedStation.get().getDockingArea().getScooterSlots().get(SLOT_NUMBER);
        assertTrue(retrievedSlot.containsScooterId(SCOOTER_ID));
    }

    @Test
    void givenNonExistentStation_whenFindByLocation_thenReturnEmptyOptional() {
        Location nonExistentLocation = Location.of("Building", "Name");

        Optional<Station> retrievedStation = stationRepository.findByLocation(nonExistentLocation);

        assertTrue(retrievedStation.isEmpty());
    }

    @Test
    void givenExistingStationWithScooterId_whenFindByScooterId_thenReturnStation() {
        Station station = stationFixture.withOccupiedSlot(SLOT_NUMBER, SCOOTER_ID)
                .withOccupiedSlot(ANOTHER_SLOT_NUMBER, ANOTHER_SCOOTER_ID).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        assertTrue(retrievedStation.isPresent());
        assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        ScooterSlot retrievedSlot =
                retrievedStation.get().getDockingArea().getScooterSlots().get(SLOT_NUMBER);
        assertTrue(retrievedSlot.containsScooterId(SCOOTER_ID));
    }

    @Test
    void givenNonExistentStationWithScooterId_whenFindByScooterId_thenReturnEmptyOptional() {
        Station station = stationFixture.withEmptySlot(SLOT_NUMBER).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        assertTrue(retrievedStation.isEmpty());
    }
}
