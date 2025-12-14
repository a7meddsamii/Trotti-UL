package ca.ulaval.glo4003.trotti.fleet.api;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.api.dto.request.*;
import ca.ulaval.glo4003.trotti.fleet.api.mapper.FleetApiMapper;
import ca.ulaval.glo4003.trotti.fleet.application.FleetMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.FleetOperationsApplicationService;
import ca.ulaval.glo4003.trotti.fleet.application.dto.*;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class FleetControllerTest {

    private static final Idul TECHNICIAN_IDUL = Idul.from("tech-123");
    private static final Idul REQUESTER_IDUL = Idul.from("user-456");
    private static final String LOCATION_STRING = "STATION-A";
    private static final Location LOCATION = Location.of(LOCATION_STRING);
    private static final TransferId TRANSFER_ID = TransferId.randomId();
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final List<SlotNumber> SLOTS = List.of(SlotNumber.from(1), SlotNumber.from(2));

    private FleetMaintenanceApplicationService maintenanceService;
    private FleetOperationsApplicationService operationsService;
    private FleetApiMapper fleetApiMapper;

    private FleetResource controller;

    @BeforeEach
    void setUp() {
        maintenanceService = Mockito.mock(FleetMaintenanceApplicationService.class);
        operationsService = Mockito.mock(FleetOperationsApplicationService.class);
        fleetApiMapper = Mockito.mock(FleetApiMapper.class);

        controller = new FleetController(maintenanceService, operationsService, fleetApiMapper);
    }

    @Test
    void givenStartTransferRequest_whenStartTransfer_thenTransferIsCreatedAndReturned() {
        StartTransferRequest request = Mockito.mock(StartTransferRequest.class);
        StartTransferDto dto = Mockito.mock(StartTransferDto.class);
        Mockito.when(fleetApiMapper.toStartTransferDto(TECHNICIAN_IDUL, request)).thenReturn(dto);
        Mockito.when(maintenanceService.startTransfer(dto)).thenReturn(TRANSFER_ID);

        Response response = controller.startTransfer(TECHNICIAN_IDUL, request);

        Mockito.verify(maintenanceService).startTransfer(dto);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(TRANSFER_ID, response.getEntity());
    }

    @Test
    void givenUnloadTransferRequest_whenUnloadTransfer_thenTransferIsUnloaded() {
        UnloadTransferRequest request = Mockito.mock(UnloadTransferRequest.class);
        UnloadTransferDto dto = Mockito.mock(UnloadTransferDto.class);
        Mockito.when(fleetApiMapper.toUnloadTransferDto(TECHNICIAN_IDUL, TRANSFER_ID.toString(),
                request)).thenReturn(dto);

        Response response =
                controller.unloadTransfer(TECHNICIAN_IDUL, TRANSFER_ID.toString(), request);

        Mockito.verify(maintenanceService).unloadTransfer(dto);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenStartMaintenanceRequest_whenStartMaintenance_thenMaintenanceIsStarted() {
        StartMaintenanceRequest request = Mockito.mock(StartMaintenanceRequest.class);
        StartMaintenanceDto dto = Mockito.mock(StartMaintenanceDto.class);
        Mockito.when(fleetApiMapper.toStartMaintenanceDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        Response response = controller.startMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(maintenanceService).startMaintenance(dto);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenEndMaintenanceRequest_whenEndMaintenance_thenMaintenanceIsEnded() {
        EndMaintenanceRequest request = Mockito.mock(EndMaintenanceRequest.class);
        EndMaintenanceDto dto = Mockito.mock(EndMaintenanceDto.class);
        Mockito.when(fleetApiMapper.toEndMaintenanceDto(TECHNICIAN_IDUL, request)).thenReturn(dto);

        Response response = controller.endMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(maintenanceService).endMaintenance(dto);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenMaintenanceRequest_whenRequestMaintenance_thenMaintenanceRequestIsSent() {
        MaintenanceRequestRequest request = Mockito.mock(MaintenanceRequestRequest.class);
        RequestMaintenanceDto dto = Mockito.mock(RequestMaintenanceDto.class);
        Mockito.when(fleetApiMapper.toRequestMaintenanceDto(REQUESTER_IDUL, request))
                .thenReturn(dto);

        Response response = controller.requestMaintenance(REQUESTER_IDUL, request);

        Mockito.verify(maintenanceService).requestMaintenance(dto);
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void givenLocation_whenGetAvailableSlots_thenAvailableSlotsAreReturned() {
        Mockito.when(operationsService.getAvailableSlots(LOCATION)).thenReturn(SLOTS);

        Response response = controller.getAvailableSlots(LOCATION_STRING);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(SLOTS, response.getEntity());
    }

    @Test
    void givenLocation_whenGetOccupiedSlots_thenOccupiedSlotsAreReturned() {
        Mockito.when(operationsService.getOccupiedSlots(LOCATION)).thenReturn(SLOTS);

        Response response = controller.getOccupiedSlots(LOCATION_STRING);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(SLOTS, response.getEntity());
    }
}
