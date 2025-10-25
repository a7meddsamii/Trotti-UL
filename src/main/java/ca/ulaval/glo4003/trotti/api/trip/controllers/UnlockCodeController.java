package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/unlock-code")
@Produces(MediaType.APPLICATION_JSON)
public class UnlockCodeController implements UnlockCodeResource {

    private final AuthenticationService authenticationService;
    private final UnlockCodeApplicationService unlockCodeApplicationService;

    public UnlockCodeController(
            AuthenticationService authenticationService,
            UnlockCodeApplicationService unlockCodeApplicationService) {
        this.authenticationService = authenticationService;
        this.unlockCodeApplicationService = unlockCodeApplicationService;
    }

    @POST
    @Path("/{ridePermitId}")
    public Response requestUnlockCode(@HeaderParam("Authorization") String tokenRequest,
            @PathParam("ridePermitId") String ridePermitId) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);

        unlockCodeApplicationService.generateUnlockCode(idul, RidePermitId.from(ridePermitId));

        return Response.ok().entity(
                new UnlockCodeResponse("Unlock Code is generated successfully and sent by e-mail."))
                .build();
    }
}
