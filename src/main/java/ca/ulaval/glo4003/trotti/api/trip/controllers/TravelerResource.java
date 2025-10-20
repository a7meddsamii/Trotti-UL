package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/traveler")
@Produces(MediaType.APPLICATION_JSON)
public class TravelerResource {

    private final RidePermitActivationApplicationService ridePermitActivationService;
    private final AuthenticationService authenticationService;

    public TravelerResource(
            RidePermitActivationApplicationService ridePermitActivationService,
            AuthenticationService authenticationService) {
        this.ridePermitActivationService = ridePermitActivationService;
        this.authenticationService = authenticationService;
    }

    @GET
    public Response getRidePermits(@HeaderParam("Authorization") String tokenRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);

        List<RidePermitDto> ridePermits = ridePermitActivationService.getRidePermit(idul);

        return Response.ok(ridePermits).build();
    }
}
