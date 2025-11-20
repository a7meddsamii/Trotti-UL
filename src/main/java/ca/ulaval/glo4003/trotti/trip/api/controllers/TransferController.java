package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.InitiateTransferRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.UnloadScootersRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TransferApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TransferApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.InitiateTransferDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.UnloadScootersDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import jakarta.ws.rs.core.Response;

public class TransferController implements TransferResource {

    private final TransferApplicationService transferApplicationService;
    private final AuthenticationService authenticationService;
    private final TransferApiMapper transferApiMapper;

    public TransferController(
            TransferApplicationService transferApplicationService,
            AuthenticationService authenticationService,
            TransferApiMapper transferApiMapper) {
        this.transferApplicationService = transferApplicationService;
        this.authenticationService = authenticationService;
        this.transferApiMapper = transferApiMapper;
    }

    @Override
    public Response initiateTransfer(String tokenHeader, InitiateTransferRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);
        InitiateTransferDto dto = transferApiMapper.toInitiateTransferDto(idul, request);

        TransferId transferId = transferApplicationService.initiateTransfer(dto);

        return Response.ok().entity(transferApiMapper.toTransferResponse(transferId)).build();
    }

    @Override
    public Response unloadScooters(String tokenHeader, String transferId, UnloadScootersRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);
        UnloadScootersDto dto = transferApiMapper.toUnloadScootersDto(idul, transferId, request);

        transferApplicationService.unloadScooters(dto);

        return Response.ok().build();
    }
}