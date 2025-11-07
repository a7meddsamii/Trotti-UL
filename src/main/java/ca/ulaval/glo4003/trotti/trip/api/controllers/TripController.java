package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
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
