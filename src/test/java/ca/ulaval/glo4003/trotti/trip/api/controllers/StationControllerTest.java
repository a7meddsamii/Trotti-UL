package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.MaintenanceRequestRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TransferResponse;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.StationMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TransferApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StationControllerTest {

    private static final String STATION_ID = "VACHON";
    private static final String TRANSFER_ID = "transfer123";
    private static final Idul TECHNICIAN_IDUL = Idul.from("tech123");
    private static final List<Integer> SLOTS = List.of(1, 2, 3);

    private TransferApplicationService transferApplicationService;
    private StationMaintenanceApplicationService stationMaintenanceApplicationService;
    private StationApiMapper stationApiMapper;
    private StationController controller;

    @BeforeEach
    void setUp() {
        transferApplicationService = Mockito.mock(TransferApplicationService.class);
        stationMaintenanceApplicationService =
                Mockito.mock(StationMaintenanceApplicationService.class);
        stationApiMapper = Mockito.mock(StationApiMapper.class);

        controller = new StationController(transferApplicationService,
                stationMaintenanceApplicationService, stationApiMapper);
    }

    @Test
    void givenValidTokenAndRequest_whenInitiateTransfer_thenReturnsOkResponse() {
        InitiateTransferRequest request = new InitiateTransferRequest(STATION_ID, SLOTS);
        InitiateTransferDto dto = Mockito.mock(InitiateTransferDto.class);
        TransferId transferId = Mockito.mock(TransferId.class);
        TransferResponse response = Mockito.mock(TransferResponse.class);

        Mockito.when(stationApiMapper.toInitiateTransferDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);
        Mockito.when(transferApplicationService.initiateTransfer(dto)).thenReturn(transferId);
        Mockito.when(stationApiMapper.toTransferResponse(transferId)).thenReturn(response);

        Response result = controller.initiateTransfer(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        Assertions.assertEquals(response, result.getEntity());
    }

    @Test
    void givenValidTokenAndRequest_whenInitiateTransfer_thenMapsRequestToDto() {
        InitiateTransferRequest request = new InitiateTransferRequest(STATION_ID, SLOTS);

        controller.initiateTransfer(TECHNICIAN_IDUL, request);

        Mockito.verify(stationApiMapper).toInitiateTransferDto(TECHNICIAN_IDUL, request);
    }

    @Test
    void givenValidTokenAndRequest_whenInitiateTransfer_thenCallsApplicationService() {
        InitiateTransferRequest request = new InitiateTransferRequest(STATION_ID, SLOTS);
        InitiateTransferDto dto = Mockito.mock(InitiateTransferDto.class);
        Mockito.when(stationApiMapper.toInitiateTransferDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        controller.initiateTransfer(TECHNICIAN_IDUL, request);

        Mockito.verify(transferApplicationService).initiateTransfer(dto);
    }

    @Test
    void givenValidTokenAndRequest_whenUnloadScooters_thenReturnsOkResponse() {
        UnloadScootersRequest request = new UnloadScootersRequest(STATION_ID, SLOTS);
        UnloadScootersDto dto = Mockito.mock(UnloadScootersDto.class);
        Mockito.when(stationApiMapper.toUnloadScootersDto(TECHNICIAN_IDUL, TRANSFER_ID, request))
                .thenReturn(dto);

        Response result = controller.unloadScooters(TECHNICIAN_IDUL, TRANSFER_ID, request);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    void givenValidTokenAndRequest_whenUnloadScooters_thenMapsRequestToDto() {
        UnloadScootersRequest request = new UnloadScootersRequest(STATION_ID, SLOTS);

        controller.unloadScooters(TECHNICIAN_IDUL, TRANSFER_ID, request);

        Mockito.verify(stationApiMapper).toUnloadScootersDto(TECHNICIAN_IDUL, TRANSFER_ID, request);
    }

    @Test
    void givenValidTokenAndRequest_whenUnloadScooters_thenCallsApplicationService() {
        UnloadScootersRequest request = new UnloadScootersRequest(STATION_ID, SLOTS);
        UnloadScootersDto dto = Mockito.mock(UnloadScootersDto.class);
        Mockito.when(stationApiMapper.toUnloadScootersDto(TECHNICIAN_IDUL, TRANSFER_ID, request))
                .thenReturn(dto);

        controller.unloadScooters(TECHNICIAN_IDUL, TRANSFER_ID, request);

        Mockito.verify(transferApplicationService).unloadScooters(dto);
    }

    @Test
    void givenValidTokenAndRequest_whenStartMaintenance_thenReturnsOkResponse() {
        StartMaintenanceRequest request = new StartMaintenanceRequest(STATION_ID);
        StartMaintenanceDto dto = Mockito.mock(StartMaintenanceDto.class);
        Mockito.when(stationApiMapper.toStartMaintenanceDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        Response result = controller.startMaintenance(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    void givenValidTokenAndRequest_whenStartMaintenance_thenMapsToDto() {
        StartMaintenanceRequest request = new StartMaintenanceRequest(STATION_ID);

        controller.startMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(stationApiMapper).toStartMaintenanceDto(TECHNICIAN_IDUL, request);
    }

    @Test
    void givenValidTokenAndRequest_whenStartMaintenance_thenCallsApplicationService() {
        StartMaintenanceRequest request = new StartMaintenanceRequest(STATION_ID);
        StartMaintenanceDto dto = Mockito.mock(StartMaintenanceDto.class);
        Mockito.when(stationApiMapper.toStartMaintenanceDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        controller.startMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(stationMaintenanceApplicationService).startMaintenance(dto);
    }

    @Test
    void givenValidTokenAndRequest_whenEndMaintenance_thenReturnsOkResponse() {
        EndMaintenanceRequest request = new EndMaintenanceRequest(STATION_ID);
        EndMaintenanceDto dto = Mockito.mock(EndMaintenanceDto.class);
        Mockito.when(stationApiMapper.toEndMaintenanceDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        Response result = controller.endMaintenance(TECHNICIAN_IDUL, request);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
    }

    @Test
    void givenValidTokenAndRequest_whenEndMaintenance_thenMapsToDto() {
        EndMaintenanceRequest request = new EndMaintenanceRequest(STATION_ID);

        controller.endMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(stationApiMapper).toEndMaintenanceDto(TECHNICIAN_IDUL, request);
    }

    @Test
    void givenValidTokenAndRequest_whenEndMaintenance_thenCallsApplicationService() {
        EndMaintenanceRequest request = new EndMaintenanceRequest(STATION_ID);
        EndMaintenanceDto dto = Mockito.mock(EndMaintenanceDto.class);
        Mockito.when(stationApiMapper.toEndMaintenanceDto(TECHNICIAN_IDUL, request))
                .thenReturn(dto);

        controller.endMaintenance(TECHNICIAN_IDUL, request);

        Mockito.verify(stationMaintenanceApplicationService).endMaintenance(dto);
    }

    @Test
    void givenValidTokenAndRequest_whenRequestMaintenance_thenCallsApplicationService() {
        MaintenanceRequestRequest request =
                new MaintenanceRequestRequest(STATION_ID, "Maintenance message");

        controller.requestMaintenanceService(TECHNICIAN_IDUL, request);

        Mockito.verify(stationMaintenanceApplicationService)
                .requestMaintenanceService(TECHNICIAN_IDUL, request.location(), request.message());
    }
}
