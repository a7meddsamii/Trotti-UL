package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndMaintenanceRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
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
    public Response initiateTransfer(String tokenHeader, InitiateTransferRequest request) {
//        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
//        Idul idul = authenticationService.authenticate(token);
        InitiateTransferDto dto = stationApiMapper.toInitiateTransferDto(null, request);

        TransferId transferId = transferApplicationService.initiateTransfer(dto);

        return Response.ok().entity(stationApiMapper.toTransferResponse(transferId)).build();
    }

    @Override
    public Response unloadScooters(String tokenHeader, String transferId,
            UnloadScootersRequest request) {
//        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
//        Idul idul = authenticationService.authenticate(token);
        UnloadScootersDto dto = stationApiMapper.toUnloadScootersDto(null, transferId, request);

        int scootersInTransit = transferApplicationService.unloadScooters(dto);

        return Response.ok().entity(stationApiMapper.toUnloadScootersResponse(scootersInTransit))
                .build();
    }

    @Override
    public Response startMaintenance(String tokenHeader, StartMaintenanceRequest request) {
//        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
//        Idul idul = authenticationService.authenticate(token);
        StartMaintenanceDto dto = stationApiMapper.toStartMaintenanceDto(null, request);

        stationMaintenanceApplicationService.startMaintenance(dto);

        return Response.ok().build();
    }

    @Override
    public Response endMaintenance(String tokenHeader, EndMaintenanceRequest request) {
//        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
//        Idul idul = authenticationService.authenticate(token);
        EndMaintenanceDto dto = stationApiMapper.toEndMaintenanceDto(null, request);

        stationMaintenanceApplicationService.endMaintenance(dto);

        return Response.ok().build();
    }
}
