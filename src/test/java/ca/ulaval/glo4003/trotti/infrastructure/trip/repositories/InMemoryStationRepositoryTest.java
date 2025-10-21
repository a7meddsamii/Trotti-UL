package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.StationFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryStationRepositoryTest {

    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final ScooterId ANOTHER_SCOOTER_ID = SCOOTER_ID.randomId();

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
        Station station = stationFixture.build();

        stationRepository.save(station);

        Optional<Station> retrievedStation =
                stationRepository.findByLocation(station.getLocation());
        assertTrue(retrievedStation.isPresent());
        assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        assertEquals(station.getCapacity(), retrievedStation.get().getCapacity());
        assertEquals(station.getDockedScooters(), retrievedStation.get().getDockedScooters());
    }

    @Test
    void givenNonExistentStation_whenFindByLocation_thenReturnEmptyOptional() {
        Location nonExistentLocation = Location.of("Building", "Name");

        Optional<Station> retrievedStation = stationRepository.findByLocation(nonExistentLocation);

        assertTrue(retrievedStation.isEmpty());
    }

    @Test
    void givenExistingStationWithScooterId_whenFindByScooterId_thenReturnStation() {
        Station station =
                stationFixture.withDockedScooters(List.of(SCOOTER_ID, ANOTHER_SCOOTER_ID)).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        assertTrue(retrievedStation.isPresent());
        assertEquals(station.getLocation(), retrievedStation.get().getLocation());
        assertEquals(station.getCapacity(), retrievedStation.get().getCapacity());
        assertEquals(station.getDockedScooters(), retrievedStation.get().getDockedScooters());
    }

    @Test
    void givenNonExistentStationWithScooterId_whenFindByScooterId_thenReturnEmptyOptional() {
        Station station = stationFixture.withDockedScooters(List.of(ANOTHER_SCOOTER_ID)).build();
        stationRepository.save(station);

        Optional<Station> retrievedStation = stationRepository.findByScooterId(SCOOTER_ID);

        assertTrue(retrievedStation.isEmpty());
    }
}
