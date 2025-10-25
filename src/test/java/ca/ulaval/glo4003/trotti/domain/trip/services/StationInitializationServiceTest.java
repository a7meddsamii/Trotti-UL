package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.factories.StationFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StationInitializationServiceTest {

    private static final String A_BUILDING = "PEPS";
    private static final String A_SPOT_NAME = "Station A";
    private static final String ANOTHER_SPOT_NAME = "Station B";
    private static final Location A_STATION_LOCATION = Location.of(A_BUILDING, A_SPOT_NAME);
    private static final Location ANOTHER_STATION_LOCATION =
            Location.of(A_BUILDING, ANOTHER_SPOT_NAME);
    private static final int STATION_CAPACITY = 10;
    private static final int EXPECTED_INITIAL_SCOOTER_COUNT = 8;

    @Mock
    private StationFactory stationFactory;

    @Mock
    private ScooterFactory scooterFactory;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private ScooterRepository scooterRepository;

    @Mock
    private Station mockStation;

    @Mock
    private Scooter mockScooter;

    private StationInitializationService stationInitializationService;

    @BeforeEach
    void setup() {
        stationInitializationService = new StationInitializationService(stationFactory,
                scooterFactory, stationRepository, scooterRepository);
    }

    @Test
    void givenStationInitializationData_whenInitializeStations_thenCreatesStationWithCorrectParameters() {
        StationConfiguration data =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        Mockito.when(stationFactory.create(A_STATION_LOCATION, STATION_CAPACITY))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(List.of());

        stationInitializationService.initializeStations(List.of(data));

        Mockito.verify(stationFactory).create(A_STATION_LOCATION, STATION_CAPACITY);
    }

    @Test
    void givenStationInitializationData_whenInitializeStations_thenCreatesCorrectNumberOfScooters() {
        StationConfiguration data =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        Mockito.when(stationFactory.create(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(List.of());

        stationInitializationService.initializeStations(List.of(data));

        Mockito.verify(scooterFactory).create(EXPECTED_INITIAL_SCOOTER_COUNT, A_STATION_LOCATION);
    }

    @Test
    void givenStationInitializationData_whenInitializeStations_thenSavesAllScootersToRepository() {
        StationConfiguration data =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        ScooterId scooterId = ScooterId.randomId();
        Mockito.when(mockScooter.getScooterId()).thenReturn(scooterId);
        List<Scooter> scooters = List.of(mockScooter, mockScooter, mockScooter);

        Mockito.when(stationFactory.create(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(data));

        Mockito.verify(scooterRepository, Mockito.times(scooters.size())).save(mockScooter);
    }

    @Test
    void givenStationInitializationData_whenInitializeStations_thenReturnsScootersToStationInOrder() {
        StationConfiguration data =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        ScooterId scooterId1 = ScooterId.randomId();
        ScooterId scooterId2 = ScooterId.randomId();

        Scooter scooter1 = Mockito.mock(Scooter.class);
        Scooter scooter2 = Mockito.mock(Scooter.class);
        Mockito.when(scooter1.getScooterId()).thenReturn(scooterId1);
        Mockito.when(scooter2.getScooterId()).thenReturn(scooterId2);

        List<Scooter> scooters = List.of(scooter1, scooter2);

        Mockito.when(stationFactory.create(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(scooters);

        stationInitializationService.initializeStations(List.of(data));

        Mockito.verify(mockStation).returnScooter(new SlotNumber(0), scooterId1);
        Mockito.verify(mockStation).returnScooter(new SlotNumber(1), scooterId2);
    }

    @Test
    void givenStationInitializationData_whenInitializeStations_thenSavesStationToRepository() {
        StationConfiguration data =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        Mockito.when(stationFactory.create(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(List.of());

        stationInitializationService.initializeStations(List.of(data));

        Mockito.verify(stationRepository).save(mockStation);
    }

    @Test
    void givenMultipleStationInitializationData_whenInitializeStations_thenInitializesAllStations() {
        StationConfiguration data1 =
                new StationConfiguration(A_BUILDING, A_SPOT_NAME, STATION_CAPACITY);
        StationConfiguration data2 =
                new StationConfiguration(A_BUILDING, ANOTHER_SPOT_NAME, STATION_CAPACITY);
        List<StationConfiguration> dataList = List.of(data1, data2);

        Station station1 = Mockito.mock(Station.class);
        Station station2 = Mockito.mock(Station.class);

        Mockito.when(stationFactory.create(A_STATION_LOCATION, STATION_CAPACITY))
                .thenReturn(station1);
        Mockito.when(stationFactory.create(ANOTHER_STATION_LOCATION, STATION_CAPACITY))
                .thenReturn(station2);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(List.of());

        stationInitializationService.initializeStations(dataList);

        Mockito.verify(stationRepository).save(station1);
        Mockito.verify(stationRepository).save(station2);
        Mockito.verify(stationFactory).create(A_STATION_LOCATION, STATION_CAPACITY);
        Mockito.verify(stationFactory).create(ANOTHER_STATION_LOCATION, STATION_CAPACITY);
    }

    @Test
    void givenCapacityOf10_whenInitializeStations_thenFillsTo80PercentCapacity() {
        int capacity = 10;
        int expectedScooters = 8;
        StationConfiguration config = new StationConfiguration(A_BUILDING, A_SPOT_NAME, capacity);

        Mockito.when(stationFactory.create(Mockito.any(), Mockito.anyInt()))
                .thenReturn(mockStation);
        Mockito.when(scooterFactory.create(Mockito.anyInt(), Mockito.any(Location.class)))
                .thenReturn(List.of());

        stationInitializationService.initializeStations(List.of(config));

        Mockito.verify(scooterFactory).create(expectedScooters, A_STATION_LOCATION);
    }
}
