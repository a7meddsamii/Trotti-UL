package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.MaintenanceRequestRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.StationApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.StationMaintenanceApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.TransferApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartMaintenanceDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import jakarta.ws.rs.core.Response;

//TODO add endpoints for getAvailable and occupied slots
public class StationController implements StationResource {

    private final TransferApplicationService transferApplicationService;
    private final StationMaintenanceApplicationService stationMaintenanceApplicationService;
    private final StationApiMapper stationApiMapper;

    public StationController(
            TransferApplicationService transferApplicationService,
            StationMaintenanceApplicationService stationMaintenanceApplicationService,
            StationApiMapper stationApiMapper) {
        this.transferApplicationService = transferApplicationService;
        this.stationMaintenanceApplicationService = stationMaintenanceApplicationService;
        this.stationApiMapper = stationApiMapper;
    }

    @Override
    public Response initiateTransfer(Idul userId, InitiateTransferRequest request) {

        InitiateTransferDto dto = stationApiMapper.toInitiateTransferDto(userId, request);

        TransferId transferId = transferApplicationService.initiateTransfer(dto);

        return Response.ok().entity(stationApiMapper.toTransferResponse(transferId)).build();
    }

    @Override
    public Response unloadScooters(Idul userId, String transferId, UnloadScootersRequest request) {

        UnloadScootersDto dto = stationApiMapper.toUnloadScootersDto(userId, transferId, request);

        int scootersInTransit = transferApplicationService.unloadScooters(dto);

        return Response.ok().entity(stationApiMapper.toUnloadScootersResponse(scootersInTransit))
                .build();
    }

    @Override
    public Response startMaintenance(Idul userId, StartMaintenanceRequest request) {

        StartMaintenanceDto dto = stationApiMapper.toStartMaintenanceDto(userId, request);

        stationMaintenanceApplicationService.startMaintenance(dto);

        return Response.ok().build();
    }

    @Override
    public Response endMaintenance(Idul userId, EndMaintenanceRequest request) {

        EndMaintenanceDto dto = stationApiMapper.toEndMaintenanceDto(userId, request);

        stationMaintenanceApplicationService.endMaintenance(dto);

        return Response.ok().build();
    }

    @Override
    public Response requestMaintenanceService(Idul userId, MaintenanceRequestRequest request) {
        stationMaintenanceApplicationService.requestMaintenanceService(userId, request.location(),
                request.message());
        return Response.noContent().build();
    }
}
