package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StationMaintenanceApplicationServiceTest {

    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final LocalDateTime EXPECTED_TIME =
            LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);

    private static final Idul TECHNICIAN_ID = Idul.from("technician123");
    private static final Location LOCATION = Mockito.mock(Location.class);
    private static final String LOCATION_STRING = "Station A";
    private static final String MESSAGE = "Docking station not working";

    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;
    private EventBus eventBus;

    private Station station;
    private Scooter scooter1;
    private Scooter scooter2;

    private StationMaintenanceApplicationService stationMaintenanceApplicationService;

    @BeforeEach
    void setup() {
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);
        eventBus = Mockito.mock(EventBus.class);

        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);

        station = Mockito.mock(Station.class);
        scooter1 = Mockito.mock(Scooter.class);
        scooter2 = Mockito.mock(Scooter.class);

        stationMaintenanceApplicationService = new StationMaintenanceApplicationService(
                stationRepository, scooterRepository, eventBus, clock);
    }

    @Test
    void givenValidStartDto_whenStartMaintenance_thenStationIsLoaded() {
        Mockito.when(stationRepository.findByLocation(LOCATION)).thenReturn(station);
        StartMaintenanceDto dto = new StartMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.startMaintenance(dto);

        Mockito.verify(stationRepository).findByLocation(LOCATION);
    }

    @Test
    void givenValidStartDto_whenStartMaintenance_thenStationStartMaintenanceIsCalled() {
        Mockito.when(stationRepository.findByLocation(LOCATION)).thenReturn(station);
        StartMaintenanceDto dto = new StartMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.startMaintenance(dto);

        Mockito.verify(station).startMaintenance(TECHNICIAN_ID);
        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenDockedScooters_whenStartMaintenance_thenScootersArePaused() {
        ScooterId id1 = ScooterId.randomId();
        ScooterId id2 = ScooterId.randomId();
        mockStationWithScooters(id1, id2);
        StartMaintenanceDto dto = new StartMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.startMaintenance(dto);

        Mockito.verify(scooter1).pauseCharging(EXPECTED_TIME);
        Mockito.verify(scooter2).pauseCharging(EXPECTED_TIME);
    }

    @Test
    void givenDockedScooters_whenStartMaintenance_thenScootersAreSaved() {
        ScooterId id1 = ScooterId.randomId();
        ScooterId id2 = ScooterId.randomId();
        mockStationWithScooters(id1, id2);
        StartMaintenanceDto dto = new StartMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.startMaintenance(dto);

        Mockito.verify(scooterRepository).saveAll(List.of(scooter1, scooter2));
    }

    @Test
    void givenValidEndDto_whenEndMaintenance_thenStationIsLoaded() {
        Mockito.when(stationRepository.findByLocation(LOCATION)).thenReturn(station);
        EndMaintenanceDto dto = new EndMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.endMaintenance(dto);

        Mockito.verify(stationRepository).findByLocation(LOCATION);
    }

    @Test
    void givenValidEndDto_whenEndMaintenance_thenStationEndMaintenanceIsCalled() {
        Mockito.when(stationRepository.findByLocation(LOCATION)).thenReturn(station);
        EndMaintenanceDto dto = new EndMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.endMaintenance(dto);

        Mockito.verify(station).endMaintenance(TECHNICIAN_ID);
        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenDockedScooters_whenEndMaintenance_thenScootersAreResumed() {
        ScooterId id1 = ScooterId.randomId();
        ScooterId id2 = ScooterId.randomId();
        mockStationWithScooters(id1, id2);
        EndMaintenanceDto dto = new EndMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.endMaintenance(dto);

        Mockito.verify(scooter1).resumeCharging(EXPECTED_TIME);
        Mockito.verify(scooter2).resumeCharging(EXPECTED_TIME);
    }

    @Test
    void givenDockedScooters_whenEndMaintenance_thenScootersAreSaved() {
        ScooterId id1 = ScooterId.randomId();
        ScooterId id2 = ScooterId.randomId();
        mockStationWithScooters(id1, id2);
        EndMaintenanceDto dto = new EndMaintenanceDto(LOCATION, TECHNICIAN_ID);

        stationMaintenanceApplicationService.endMaintenance(dto);

        Mockito.verify(scooterRepository).saveAll(List.of(scooter1, scooter2));
    }

    @Test
    void givenValidStationAndMessage_whenRequestMaintenance_thenPublishEvent() {
        Mockito.when(stationRepository.findByLocation(Mockito.any())).thenReturn(station);
        Mockito.when(station.isUnderMaintenance()).thenReturn(false);

        stationMaintenanceApplicationService.requestMaintenanceService(TECHNICIAN_ID,
                LOCATION_STRING, MESSAGE);

        Mockito.verify(eventBus).publish(Mockito.any(MaintenanceRequestedEvent.class));
    }

    @Test
    void givenStationUnderMaintenance_whenRequestMaintenance_thenDoesNotPublish() {
        Mockito.when(stationRepository.findByLocation(Mockito.any())).thenReturn(station);
        Mockito.when(station.isUnderMaintenance()).thenReturn(true);

        Mockito.verify(eventBus, Mockito.never())
                .publish(Mockito.any(MaintenanceRequestedEvent.class));
    }

    private void mockStationWithScooters(ScooterId id1, ScooterId id2) {
        Mockito.when(stationRepository.findByLocation(LOCATION)).thenReturn(station);
        Mockito.when(station.getAllScooterIds()).thenReturn(List.of(id1, id2));
        Mockito.when(scooterRepository.findByIds(List.of(id1, id2)))
                .thenReturn(List.of(scooter1, scooter2));
    }
}
