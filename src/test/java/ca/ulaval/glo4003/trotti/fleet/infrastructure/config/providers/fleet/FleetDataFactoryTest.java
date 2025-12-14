package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.fleet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.FleetDataFactory;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.StationDataRecord;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class FleetDataFactoryTest {

    private static final String A_BUILDING = "VACHON";
    private static final String A_SPOT_NAME = "Main Entrance";
    private static final int A_CAPACITY = 10;
    private static final int EXPECTED_INITIAL_SCOOTER_COUNT = 8;
    private static final Instant FIXED_TIME = Instant.parse("2024-01-01T10:00:00Z");

    private FleetDataFactory fleetDataFactory;
    private StationFactory stationFactory;
    private ScooterFactory scooterFactory;
    private FleetRepository fleetRepository;

    private Station mockStation;
    private List<Scooter> mockScooters;

    @BeforeEach
    void setup() {
        stationFactory = Mockito.mock(StationFactory.class);
        scooterFactory = Mockito.mock(ScooterFactory.class);
        fleetRepository = Mockito.mock(FleetRepository.class);
        Clock clock = Clock.fixed(FIXED_TIME, Clock.systemDefaultZone().getZone());

        fleetDataFactory =
                new FleetDataFactory(stationFactory, scooterFactory, fleetRepository, clock);

        mockStation = Mockito.mock(Station.class);
        mockScooters = createMockScooters();
    }

    @Test
    void givenEmptyStationDataList_whenRun_thenNoStationsAreCreated() {
        List<StationDataRecord> emptyList = List.of();

        fleetDataFactory.run(emptyList);

        verify(stationFactory, never()).create(any(), anyInt());
    }

    @Test
    void givenSingleStationData_whenRun_thenCreatesStationWithCorrectLocation() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        fleetDataFactory.run(List.of(record));
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

        fleetDataFactory.run(List.of(record));

        verify(mockStation).calculateInitialScooterCount();
        verify(scooterFactory).create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class));
    }

    @Test
    void givenStationData_whenRun_thenDocksAllScootersInStation() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        fleetDataFactory.run(List.of(record));

        for (int i = 0; i < mockScooters.size(); i++) {
            SlotNumber expectedSlot = new SlotNumber(i);
            Scooter expectedScooter = mockScooters.get(i);
            verify(mockStation).parkScooter(eq(expectedSlot), eq(expectedScooter),
                    any(LocalDateTime.class));
        }
    }

    @Test
    void givenStationData_whenRun_thenSavesFleetToRepository() {
        StationDataRecord record = new StationDataRecord(A_BUILDING, A_SPOT_NAME, A_CAPACITY);
        when(stationFactory.create(any(Location.class), eq(A_CAPACITY))).thenReturn(mockStation);
        when(mockStation.calculateInitialScooterCount()).thenReturn(EXPECTED_INITIAL_SCOOTER_COUNT);
        when(scooterFactory.create(eq(EXPECTED_INITIAL_SCOOTER_COUNT), any(Location.class)))
                .thenReturn(mockScooters);

        fleetDataFactory.run(List.of(record));

        ArgumentCaptor<Fleet> fleetCaptor = ArgumentCaptor.forClass(Fleet.class);
        verify(fleetRepository).save(fleetCaptor.capture());

        Fleet savedFleet = fleetCaptor.getValue();
        assertNotNull(savedFleet);
        assertEquals(1, savedFleet.getStations().size());
        assertTrue(savedFleet.getStations().containsValue(mockStation));
    }

    private List<Scooter> createMockScooters() {
        List<Scooter> scooters = new ArrayList<>();
        for (int i = 0; i < FleetDataFactoryTest.EXPECTED_INITIAL_SCOOTER_COUNT; i++) {
            Scooter scooter = Mockito.mock(Scooter.class);
            when(scooter.getScooterId()).thenReturn(ScooterId.randomId());
            scooters.add(scooter);
        }
        return scooters;
    }
}
