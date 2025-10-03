package ca.ulaval.glo4003.trotti.api.resources;

import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import jakarta.ws.rs.*;
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
