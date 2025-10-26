package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/trips")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TripResource {

    private final TripApplicationService tripApplicationService;
    private final AuthenticationService authenticationService;

    public TripResource(
            TripApplicationService tripApplicationService,
            AuthenticationService authenticationService) {
        this.tripApplicationService = tripApplicationService;
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("/start")
    public Response startTrip(@HeaderParam("Authorization") String tokenHeader,
            @Valid StartTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);

        // tripApplicationService.startTrip

        return Response.ok().build();
    }

    @POST
    @Path("/end")
    public Response endTrip(@HeaderParam("Authorization") String tokenHeader,
            @Valid EndTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);

        return Response.noContent().build();
    }
}
