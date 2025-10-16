package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StationInitializationServiceTest {

    private static final Location A_STATION_LOCATION = Location.of("PEPS", "Station A");
    private static final Location ANOTHER_STATION_LOCATION = Location.of("PEPS", "Station B");
    private static final int STATION_CAPACITY = 10;
    private static final int EXPECTED_INITIAL_SCOOTER_COUNT = 8;

    @Mock
    private ScooterFactory scooterFactory;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private ScooterRepository scooterRepository;

    @Mock
    private Scooter mockScooter;

    private StationInitializationService stationInitializationService;
    private Station station;

    @BeforeEach
    void setup() {
        stationInitializationService = new StationInitializationService(scooterFactory,
                stationRepository, scooterRepository);
        station = new Station(A_STATION_LOCATION, STATION_CAPACITY);
    }

    @Test
    void givenStations_whenInitializeStations_thenCreatesCorrectNumberOfScootersPerStation() {
        List<Scooter> scooters = List.of(mockScooter, mockScooter, mockScooter);
        Mockito.when(scooterFactory.createScooters(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(station));

        Mockito.verify(scooterFactory).createScooters(EXPECTED_INITIAL_SCOOTER_COUNT,
                A_STATION_LOCATION);
    }

    @Test
    void givenStations_whenInitializeStations_thenSavesAllScootersToRepository() {
        List<Scooter> scooters = List.of(mockScooter, mockScooter, mockScooter);
        Mockito.when(scooterFactory.createScooters(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(station));

        Mockito.verify(scooterRepository, Mockito.times(scooters.size())).save(mockScooter);
    }

    @Test
    void givenStations_whenInitializeStations_thenAddsScooterIdsToStations() {
        Id scooterId = Id.randomId();
        Scooter scooter = Mockito.mock(Scooter.class);
        Mockito.when(scooter.getId()).thenReturn(scooterId);
        List<Scooter> scooters = List.of(scooter);
        Mockito.when(scooterFactory.createScooters(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(station));

        Assertions.assertTrue(station.getScooterIds().contains(scooterId));
    }

    @Test
    void givenStations_whenInitializeStations_thenSavesAllStationsToRepository() {
        List<Scooter> scooters = List.of(mockScooter);
        Mockito.when(scooterFactory.createScooters(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(station));

        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenMultipleStations_whenInitializeStations_thenInitializesAllStations() {
        Station station1 = new Station(A_STATION_LOCATION, STATION_CAPACITY);
        Station station2 = new Station(ANOTHER_STATION_LOCATION, STATION_CAPACITY);
        List<Station> stations = List.of(station1, station2);

        List<Scooter> scooters = List.of(mockScooter);
        Mockito.when(scooterFactory.createScooters(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(stations);

        Mockito.verify(stationRepository, Mockito.times(2)).save(Mockito.any(Station.class));
    }
}
