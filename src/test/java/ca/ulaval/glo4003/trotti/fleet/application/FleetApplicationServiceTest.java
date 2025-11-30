package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FleetApplicationServiceTest {
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final LocalDateTime EXPECTED_TIME =
            LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);

    private static final Location A_LOCATION = Location.of("PEPS", "Station A");
    private static final Idul IDUL = Idul.from("abcd");
    private static final SlotNumber A_SLOT = new SlotNumber(1);
    private static final ScooterId A_SCOOTER_ID = Mockito.mock(ScooterId.class);
    private static final String A_MESSAGE = "maintenance-message";

    private FleetRepository fleetRepository;
    private EventBus eventBus;
    private Fleet fleet;

    private FleetApplicationService fleetApplicationService;

    @BeforeEach
    void setup() {
        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);

        fleetRepository = Mockito.mock(FleetRepository.class);
        eventBus = Mockito.mock(EventBus.class);
        fleet = Mockito.mock(Fleet.class);

        Mockito.when(fleetRepository.getFleet()).thenReturn(fleet);

        fleetApplicationService = new FleetApplicationService(fleetRepository, eventBus, clock);
    }

    @Test
    void givenRentScooterDto_whenRentScooter_thenFleetIsSaved() {
        RentScooterDto rentScooterDto = new RentScooterDto(A_LOCATION, A_SLOT);
        Mockito.when(fleet.rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME)).thenReturn(A_SCOOTER_ID);

        fleetApplicationService.rentScooter(rentScooterDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenRentScooterDto_whenRentScooter_thenFleetRentScooterIsCalled() {
        RentScooterDto rentScooterDto = new RentScooterDto(A_LOCATION, A_SLOT);
        Mockito.when(fleet.rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME)).thenReturn(A_SCOOTER_ID);

        fleetApplicationService.rentScooter(rentScooterDto);

        Mockito.verify(fleet).rentScooter(A_LOCATION, A_SLOT, EXPECTED_TIME);
    }

    @Test
    void givenReturnScooterDto_whenReturnScooter_thenFleetReturnScooterIsCalled() {
        ReturnScooterDto returnScooterDto = new ReturnScooterDto(A_SCOOTER_ID, A_LOCATION, A_SLOT);

        fleetApplicationService.returnScooter(returnScooterDto);

        Mockito.verify(fleet).returnScooter(A_SCOOTER_ID, A_LOCATION, A_SLOT, EXPECTED_TIME);
    }

    @Test
    void givenReturnScooterDto_whenReturnScooter_thenFleetIsSaved() {
        ReturnScooterDto returnScooterDto = new ReturnScooterDto(A_SCOOTER_ID, A_LOCATION, A_SLOT);

        fleetApplicationService.returnScooter(returnScooterDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenStartMaintenanceDto_whenStartMaintenance_thenFleetStartMaintenanceIsCalled() {
        StartMaintenanceDto startMaintenanceDto = new StartMaintenanceDto(A_LOCATION,
                Mockito.mock(ca.ulaval.glo4003.trotti.commons.domain.Idul.class));

        fleetApplicationService.startMaintenance(startMaintenanceDto);

        Mockito.verify(fleet).startMaintenance(startMaintenanceDto.location(),
                startMaintenanceDto.technicianId(), EXPECTED_TIME);
    }

    @Test
    void givenStartMaintenanceDto_whenStartMaintenance_thenFleetIsSaved() {
        StartMaintenanceDto startMaintenanceDto = new StartMaintenanceDto(A_LOCATION, IDUL);

        fleetApplicationService.startMaintenance(startMaintenanceDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenEndMaintenanceDto_whenEndMaintenance_thenFleetEndMaintenanceIsCalled() {
        EndMaintenanceDto endMaintenanceDto =
                new EndMaintenanceDto(A_LOCATION, Mockito.mock(Idul.class));

        fleetApplicationService.endMaintenance(endMaintenanceDto);

        Mockito.verify(fleet).endMaintenance(endMaintenanceDto.location(),
                endMaintenanceDto.technicianId(), EXPECTED_TIME);
    }

    @Test
    void givenEndMaintenanceDto_whenEndMaintenance_thenFleetIsSaved() {
        EndMaintenanceDto endMaintenanceDto = new EndMaintenanceDto(A_LOCATION, IDUL);

        fleetApplicationService.endMaintenance(endMaintenanceDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenRequestMaintenanceDto_whenRequestMaintenance_thenFleetEnsureNotUnderMaintenanceIsCalled() {
        RequestMaintenanceDto requestMaintenanceDto =
                new RequestMaintenanceDto(IDUL, A_LOCATION, A_MESSAGE);

        fleetApplicationService.requestMaintenance(requestMaintenanceDto);

        Mockito.verify(fleet).ensureStationNotUnderMaintenance(A_LOCATION);
    }

    @Test
    void givenRequestMaintenanceDto_whenRequestMaintenance_thenEventIsPublished() {
        RequestMaintenanceDto requestMaintenanceDto =
                new RequestMaintenanceDto(IDUL, A_LOCATION, A_MESSAGE);

        fleetApplicationService.requestMaintenance(requestMaintenanceDto);

        Mockito.verify(eventBus).publish(Mockito.any(MaintenanceRequestedEvent.class));
    }

    @Test
    void givenStartTransferDto_whenStartTransfer_thenFleetStartTransferIsCalled() {
        StartTransferDto startTransferDto = new StartTransferDto(IDUL, A_LOCATION, List.of(A_SLOT));

        fleetApplicationService.startTransfer(startTransferDto);

        Mockito.verify(fleet).startTransfer(IDUL, A_LOCATION, List.of(A_SLOT));
    }

    @Test
    void givenStartTransferDto_whenStartTransfer_thenFleetIsSaved() {
        StartTransferDto startTransferDto = new StartTransferDto(IDUL, A_LOCATION, List.of(A_SLOT));

        fleetApplicationService.startTransfer(startTransferDto);

        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenUnloadTransferDto_whenUnloadTransfer_thenFleetUnloadTransferIsCalled() {
        UnloadTransferDto unloadTransferDto =
                new UnloadTransferDto(IDUL, A_LOCATION, List.of(A_SLOT));

        fleetApplicationService.unloadTransfer(unloadTransferDto);

        Mockito.verify(fleet).unloadTransfer(IDUL, A_LOCATION, List.of(A_SLOT), EXPECTED_TIME);
    }

    @Test
    void givenUnloadTransferDto_whenUnloadTransfer_thenFleetIsSaved() {
        UnloadTransferDto unloadTransferDto =
                new UnloadTransferDto(IDUL, A_LOCATION, List.of(A_SLOT));

        fleetApplicationService.unloadTransfer(unloadTransferDto);

        Mockito.verify(fleetRepository).save(fleet);
    }
}
