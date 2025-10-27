package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.application.trip.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public class TripController implements TripResource {

    private final TripApplicationService tripApplicationService;
    private final AuthenticationService authenticationService;
    private final TripApiMapper tripApiMapper;

    public TripController(
            TripApplicationService tripApplicationService,
            AuthenticationService authenticationService,
            TripApiMapper tripApiMapper) {
        this.tripApplicationService = tripApplicationService;
        this.authenticationService = authenticationService;
        this.tripApiMapper = tripApiMapper;
    }

    @Override
    public Response startTrip(String tokenHeader, StartTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);
        StartTripDto startTripDto = tripApiMapper.toStartTripDto(idul, request);

        tripApplicationService.startTrip(startTripDto);

        return Response.ok().build();
    }

    @Override
    public Response endTrip(String tokenHeader, EndTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);
        EndTripDto endTripDto = tripApiMapper.toEndTripDto(idul, request);

        tripApplicationService.endTrip(endTripDto);

        return Response.ok().build();
    }
}
