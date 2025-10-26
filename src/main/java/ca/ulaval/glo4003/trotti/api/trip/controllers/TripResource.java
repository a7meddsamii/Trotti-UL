package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
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
    private final TripApiMapper tripApiMapper;

    public TripResource(
            TripApplicationService tripApplicationService,
            AuthenticationService authenticationService,
            TripApiMapper tripApiMapper
    ) {
        this.tripApplicationService = tripApplicationService;
        this.authenticationService = authenticationService;
        this.tripApiMapper = tripApiMapper;
    }

    @POST
    @Path("/start")
    public Response startTrip(@HeaderParam("Authorization") String tokenHeader,
                              @Valid StartTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);

        tripApplicationService.startTrip(
                idul,
                request.ridePermitId(),
                request.unlockCode(),
                request.location(),
                request.slotNumber()
        );

        return Response.ok().build();
    }

    @POST
    @Path("/end")
    public Response endTrip(@HeaderParam("Authorization") String tokenHeader,
                            @Valid EndTripRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        Idul idul = authenticationService.authenticate(token);

        tripApplicationService.endTrip(
                idul,
                request.slotNumber(),
                request.location()
        );

        return Response.noContent().build();
    }
}
