package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.application.dto.DockScooterDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UndockScooterDto;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DockingAndUndockingApplicationServiceTest {

    private static final Location STATION_LOCATION = Location.of("VACHON", "Entrée 1");
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");

    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;

    private Station station;
    private Scooter scooter;
    private DockingAndUndockingApplicationService dockingAndUndockingApplicationService;

    @BeforeEach
    void setUp() {
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);
        station = Mockito.mock(Station.class);
        scooter = Mockito.mock(Scooter.class);

        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);

        Mockito.when(stationRepository.findByLocation(STATION_LOCATION)).thenReturn(station);
        Mockito.when(station.getScooter(SLOT_NUMBER)).thenReturn(SCOOTER_ID);
        Mockito.when(scooterRepository.findById(SCOOTER_ID)).thenReturn(scooter);

        dockingAndUndockingApplicationService = new DockingAndUndockingApplicationService(
                stationRepository, scooterRepository, clock);
    }

    @Test
    void givenValidParameters_whenUndock_thenReturnScooterId() {
        Mockito.when(station.getScooter(SLOT_NUMBER)).thenReturn(SCOOTER_ID);
        UndockScooterDto dto = new UndockScooterDto(STATION_LOCATION, SLOT_NUMBER);

        ScooterId result = dockingAndUndockingApplicationService.undock(dto);

        Assertions.assertEquals(SCOOTER_ID, result);
    }

    @Test
    void givenValidParameters_whenUndock_thenScooterUndockIsCalled() {
        UndockScooterDto dto = new UndockScooterDto(STATION_LOCATION, SLOT_NUMBER);

        dockingAndUndockingApplicationService.undock(dto);

        Mockito.verify(scooter).undock(LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC));
    }

    @Test
    void givenValidParameters_whenDock_thenScooterIsReturnedToStation() {
        DockScooterDto dto = new DockScooterDto(STATION_LOCATION, SLOT_NUMBER, SCOOTER_ID);

        dockingAndUndockingApplicationService.dock(dto);

        Mockito.verify(station).returnScooter(SLOT_NUMBER, SCOOTER_ID);
    }

    @Test
    void givenValidParameters_whenUndock_thenStationIsSaved() {
        Mockito.when(station.getScooter(SLOT_NUMBER)).thenReturn(SCOOTER_ID);
        UndockScooterDto dto = new UndockScooterDto(STATION_LOCATION, SLOT_NUMBER);

        dockingAndUndockingApplicationService.undock(dto);

        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenValidParameters_whenUndock_thenScooterIsSaved() {
        UndockScooterDto dto = new UndockScooterDto(STATION_LOCATION, SLOT_NUMBER);

        dockingAndUndockingApplicationService.undock(dto);

        Mockito.verify(scooterRepository).save(scooter);
    }

    @Test
    void givenValidParameters_whenDock_thenStationIsSaved() {
        DockScooterDto dto = new DockScooterDto(STATION_LOCATION, SLOT_NUMBER, SCOOTER_ID);

        dockingAndUndockingApplicationService.dock(dto);

        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenValidParameters_whenDock_thenScooterIsSaved() {
        DockScooterDto dto = new DockScooterDto(STATION_LOCATION, SLOT_NUMBER, SCOOTER_ID);

        dockingAndUndockingApplicationService.dock(dto);

        Mockito.verify(scooterRepository).save(scooter);
    }

    @Test
    void givenValidParameters_whenDock_thenScooterDockAtIsCalled() {
        DockScooterDto dto = new DockScooterDto(STATION_LOCATION, SLOT_NUMBER, SCOOTER_ID);

        dockingAndUndockingApplicationService.dock(dto);

        Mockito.verify(scooter).dockAt(Mockito.eq(STATION_LOCATION),
                Mockito.eq(LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC)));
    }

    @Test
    void givenLocation_whenFindAvailableSlots_thenStationIsLoaded() {
        dockingAndUndockingApplicationService.findAvailableSlotsInStation(STATION_LOCATION);

        Mockito.verify(stationRepository).findByLocation(STATION_LOCATION);
    }

    @Test
    void givenLocation_whenFindAvailableSlots_thenReturnAvailableSlots() {
        List<SlotNumber> availableSlots = List.of(new SlotNumber(2), new SlotNumber(3));
        Mockito.when(station.getAvailableSlots()).thenReturn(availableSlots);

        List<SlotNumber> result =
                dockingAndUndockingApplicationService.findAvailableSlotsInStation(STATION_LOCATION);

        Assertions.assertEquals(availableSlots, result);
    }

    @Test
    void givenLocation_whenFindOccupiedSlots_thenStationIsLoaded() {
        dockingAndUndockingApplicationService.findOccupiedSlotsInStation(STATION_LOCATION);

        Mockito.verify(stationRepository).findByLocation(STATION_LOCATION);
    }

    @Test
    void givenLocation_whenFindOccupiedSlots_thenReturnOccupiedSlots() {
        List<SlotNumber> occupiedSlots = List.of(new SlotNumber(4));
        Mockito.when(station.getOccupiedSlots()).thenReturn(occupiedSlots);

        List<SlotNumber> result =
                dockingAndUndockingApplicationService.findOccupiedSlotsInStation(STATION_LOCATION);

        Assertions.assertEquals(occupiedSlots, result);
    }
}
