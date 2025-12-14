package ca.ulaval.glo4003.trotti.fleet.application;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.TransferFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FleetMaintenanceApplicationServiceTest {

    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final LocalDateTime EXPECTED_TIME =
            LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);
    private static final Location SOURCE_LOCATION = Location.of("PEPS", "Source Station");
    private static final Location DESTINATION_LOCATION = Location.of("PEPS", "Destination Station");
    private static final Idul TECHNICIAN_ID = Idul.from("abcd");
    private static final Idul REQUESTER_ID = Idul.from("efgh");
    private static final SlotNumber SLOT_ONE = new SlotNumber(1);
    private static final SlotNumber SLOT_TWO = new SlotNumber(2);
    private static final List<SlotNumber> SOURCE_SLOTS = List.of(SLOT_ONE, SLOT_TWO);
    private static final List<SlotNumber> DESTINATION_SLOTS = List.of(SLOT_ONE, SLOT_TWO);

    private static final ScooterId SCOOTER_ID_ONE = Mockito.mock(ScooterId.class);
    private static final ScooterId SCOOTER_ID_TWO = Mockito.mock(ScooterId.class);
    private static final List<ScooterId> SCOOTER_IDS = List.of(SCOOTER_ID_ONE, SCOOTER_ID_TWO);
    private static final TransferId TRANSFER_ID = Mockito.mock(TransferId.class);
    private static final String MAINTENANCE_MESSAGE = "maintenance-message";

    private FleetRepository fleetRepository;
    private TransferRepository transferRepository;
    private EventBus eventBus;
    private TransferFactory transferFactory;

    private Fleet fleet;
    private Transfer transfer;

    private FleetMaintenanceApplicationService fleetMaintenanceApplicationService;

    @BeforeEach
    void setup() {
        Clock clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);

        fleetRepository = Mockito.mock(FleetRepository.class);
        transferRepository = Mockito.mock(TransferRepository.class);
        transferFactory = Mockito.mock(TransferFactory.class);
        eventBus = Mockito.mock(EventBus.class);

        fleet = Mockito.mock(Fleet.class);
        transfer = Mockito.mock(Transfer.class);

        Mockito.when(fleetRepository.find()).thenReturn(fleet);

        fleetMaintenanceApplicationService = new FleetMaintenanceApplicationService(fleetRepository,
                transferRepository, transferFactory, eventBus, clock);
    }

    @Test
    void givenStartMaintenanceDto_whenStartMaintenance_thenFleetStartsMaintenanceAndIsSaved() {
        StartMaintenanceDto dto = new StartMaintenanceDto(SOURCE_LOCATION, TECHNICIAN_ID);

        fleetMaintenanceApplicationService.startMaintenance(dto);

        Mockito.verify(fleet).startMaintenance(SOURCE_LOCATION, TECHNICIAN_ID, EXPECTED_TIME);
        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenEndMaintenanceDto_whenEndMaintenance_thenFleetEndsMaintenanceAndIsSaved() {
        EndMaintenanceDto dto = new EndMaintenanceDto(SOURCE_LOCATION, TECHNICIAN_ID);

        fleetMaintenanceApplicationService.endMaintenance(dto);

        Mockito.verify(fleet).endMaintenance(SOURCE_LOCATION, TECHNICIAN_ID, EXPECTED_TIME);
        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenStartTransferDto_whenStartTransfer_thenTransferIsCreatedAndSaved() {
        StartTransferDto dto = new StartTransferDto(TECHNICIAN_ID, SOURCE_LOCATION, SOURCE_SLOTS);

        Mockito.when(fleet.retrieveScooters(SOURCE_LOCATION, SOURCE_SLOTS)).thenReturn(SCOOTER_IDS);
        Mockito.when(transferFactory.create(TECHNICIAN_ID, SCOOTER_IDS)).thenReturn(transfer);
        Mockito.when(transfer.getTransferId()).thenReturn(TRANSFER_ID);

        TransferId result = fleetMaintenanceApplicationService.startTransfer(dto);

        Assertions.assertEquals(TRANSFER_ID, result);
        Mockito.verify(transferRepository).save(transfer);
        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenUnloadTransferDto_whenUnloadTransfer_thenScootersAreDepositedAndSaved() {
        UnloadTransferDto dto = new UnloadTransferDto(TRANSFER_ID, TECHNICIAN_ID,
                DESTINATION_LOCATION, DESTINATION_SLOTS);

        Mockito.when(transferRepository.findById(TRANSFER_ID))
                .thenReturn(Optional.ofNullable(transfer));
        Mockito.when(transfer.unload(TECHNICIAN_ID, DESTINATION_SLOTS.size()))
                .thenReturn(SCOOTER_IDS);

        fleetMaintenanceApplicationService.unloadTransfer(dto);

        Mockito.verify(fleet).depositScooters(DESTINATION_LOCATION, DESTINATION_SLOTS, SCOOTER_IDS,
                EXPECTED_TIME);
        Mockito.verify(transferRepository).save(transfer);
        Mockito.verify(fleetRepository).save(fleet);
    }

    @Test
    void givenRequestMaintenanceDto_whenRequestMaintenance_thenEventIsPublished() {
        RequestMaintenanceDto dto =
                new RequestMaintenanceDto(REQUESTER_ID, SOURCE_LOCATION, MAINTENANCE_MESSAGE);

        fleetMaintenanceApplicationService.requestMaintenance(dto);

        Mockito.verify(fleet).ensureStationNotUnderMaintenance(SOURCE_LOCATION);
        Mockito.verify(eventBus).publish(Mockito.any(MaintenanceRequestedEvent.class));
    }
}
