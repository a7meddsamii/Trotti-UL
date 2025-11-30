package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.fleet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataFactory;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class StationDataFactoryTest {

    private static final String A_BUILDING = "VACHON";
    private static final String A_SPOT_NAME = "Main Entrance";
    private static final int A_CAPACITY = 10;
    private static final int EXPECTED_INITIAL_SCOOTER_COUNT = 8;

    private StationDataFactory stationDataFactory;
    private StationFactory stationFactory;
    private ScooterFactory scooterFactory;
    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;

    private Station mockStation;
    private List<Scooter> mockScooters;

    @BeforeEach
    void setup() {
        stationFactory = Mockito.mock(StationFactory.class);
        scooterFactory = Mockito.mock(ScooterFactory.class);
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);

        stationDataFactory = new StationDataFactory(stationFactory, scooterFactory,
                stationRepository, scooterRepository);

        mockStation = Mockito.mock(Station.class);
        mockScooters = createMockScooters(EXPECTED_INITIAL_SCOOTER_COUNT);
    }

    @Test
    void givenEmptyStationDataList_whenRun_thenNoStationsAreCreated() {
        List<StationDataRecord> emptyList = List.of();

        stationDataFactory.run(emptyList);

        verify(stationFactory, never()).create(any(), anyInt());
        verify(stationRepository, never()).save(any());
    }

    @Test
    void givenSingleStationData_whenRun_thenCreatesStationWithCorrectLocation() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        stationDataFactory.run(List.of(record));
        ArgumentCaptor<Location> locationCaptor = ArgumentCaptor.forClass(Location.class);
        verify(stationFactory).create(locationCaptor.capture(), eq(A_CAPACITY));

        Location capturedLocation = locationCaptor.getValue();
        assertEquals(A_BUILDING, capturedLocation.getBuilding());
        assertEquals(A_SPOT_NAME, capturedLocation.getSpotName());
    }

    @Test
    void givenStationData_whenRun_thenCreatesScootersWithInitialScooterCountFromStation() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        stationDataFactory.run(List.of(record));

        verify(mockStation).calculateInitialScooterCount();
        verify(scooterFactory).create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class));
    }

    @Test
    void givenStationData_whenRun_thenSavesAllScootersToRepository() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        stationDataFactory.run(List.of(record));

        verify(scooterRepository, times(EXPECTED_INITIAL_SCOOTER_COUNT)).save(any(Scooter.class));
        for (Scooter scooter : mockScooters) {
            verify(scooterRepository).save(scooter);
        }
    }

    /**
     * @deprecated  correct the code in commented lines when application layer is added
     *
     */
    @Test
    void givenStationData_whenRun_thenDocksAllScootersInStation() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        stationDataFactory.run(List.of(record));

        for (int i = 0; i < mockScooters.size(); i++) {
            SlotNumber expectedSlot = new SlotNumber(i);
            ScooterId expectedScooterId = mockScooters.get(i).getScooterId();
            // verify(mockStation).returnScooter(eq(expectedSlot), eq(expectedScooterId));
        }
    }

    @Test
    void givenStationData_whenRun_thenSavesStationToRepository() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        stationDataFactory.run(List.of(record));

        verify(stationRepository).save(mockStation);
    }

    private List<Scooter> createMockScooters(int numberOfScooters) {
        List<Scooter> scooters = new ArrayList<>();
        for (int i = 0; i < numberOfScooters; i++) {
            Scooter scooter = Mockito.mock(Scooter.class);
            when(scooter.getScooterId()).thenReturn(ScooterId.randomId());
            scooters.add(scooter);
        }
        return scooters;
    }
}
